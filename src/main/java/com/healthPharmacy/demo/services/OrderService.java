package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.CartItemDTO;
import com.healthPharmacy.demo.dto.CartResponseDTO;
import com.healthPharmacy.demo.dto.ProductOrderRequestDTO;
import com.healthPharmacy.demo.enums.OrderStatus;
import com.healthPharmacy.demo.enums.ProductSort;
import com.healthPharmacy.demo.models.*;
import com.healthPharmacy.demo.repository.CustomerRepository;
import com.healthPharmacy.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerRepository customerRepository;
    private final PersonService personService;

    public OrderService(OrderRepository orderRepository, ProductService productService, CustomerRepository customerRepository, PersonService personService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerRepository = customerRepository;
        this.personService = personService;
    }

    @Transactional
    public void buyNow(ProductOrderRequestDTO productOrderRequestDTO){
        OrderModel order = new OrderModel();
        Object principal = personService.getAuthenticatedPerson();

        if (principal instanceof PersonModel person) {
            CustomerModel customer = customerRepository.findByPersonModel(person)
                    .orElseThrow(() -> new AccessDeniedException("Customer profile not found for this user."));

            order.setCustomer(customer);
        } else {
            throw new AccessDeniedException("Only authenticated users can perform this operation");
        }
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.COMPLETED);

        ProductModel productModel = productService.findProductModelByBarcode(productOrderRequestDTO.productBarcode());
        productService.haveInStock(productOrderRequestDTO.productBarcode(),  productOrderRequestDTO.quantity());
        CartItemModel item = getCartItemModel(productOrderRequestDTO, productModel, order);


        order.setItems(List.of(item));
        order.setTotalValue(item.getPrice());

        productService.productPurchased(productOrderRequestDTO.productBarcode(), item.getQuantity());

        orderRepository.save(order);
    }

    @Transactional
    public void addToCart(ProductOrderRequestDTO request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof PersonModel person)) {
            throw new AccessDeniedException("Only authenticated users can perform this operation");
        }

        CustomerModel customer = customerRepository.findByPersonModel(person)
                .orElseThrow(() -> new AccessDeniedException("Customer profile not found for this user."));

        OrderModel order = orderRepository.findByCustomerAndStatus(customer, OrderStatus.OPEN)
                .orElseGet(() -> {
                    OrderModel newOrder = new OrderModel();
                    newOrder.setCustomer(customer);
                    newOrder.setOrderDate(LocalDateTime.now());
                    newOrder.setStatus(OrderStatus.OPEN);
                    return orderRepository.save(newOrder);
                });

        ProductModel productModel = productService.findProductModelByBarcode(request.productBarcode());

        CartItemModel existingItem = order.getItems().stream()
                .filter(item -> item.getProduct().equals(productModel))
                .findFirst()
                .orElse(null);

        int quantityToAdd = request.getSafeQuantity();

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantityToAdd;
            existingItem.setQuantity(newQuantity);
            existingItem.setPrice(productModel.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
        } else {
            CartItemModel newItem = getCartItemModel(request, productModel, order);
            order.getItems().add(newItem);
        }

        updateTotalValue(order);
    }

    @Transactional
    public void decreaseQuantity(ProductOrderRequestDTO request) {
        CustomerModel customer = new CustomerModel();
        OrderModel order = new OrderModel();
        Object principal = personService.getAuthenticatedPerson();
        if (principal instanceof PersonModel person) {
            customer = customerRepository.findByPersonModel(person)
                    .orElseThrow(() -> new AccessDeniedException("Customer profile not found for this user."));
            order.setCustomer(customer);
        } else {
            throw new AccessDeniedException("Only authenticated users can perform this operation");
        }

        order = orderRepository.findByCustomerAndStatus(customer, OrderStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("No open order found"));

        ProductModel product = productService.findProductModelByBarcode(request.productBarcode());

        CartItemModel item = order.getItems().stream()
                .filter(i -> i.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (item.getQuantity() > request.quantity()) {
            int newQty = item.getQuantity() - request.quantity();
            item.setQuantity(newQty);
            item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(newQty)));
        } else {
            order.getItems().remove(item);
        }

        updateTotalValue(order);
    }

    @Transactional
    public CartResponseDTO viewCart(int page, int size, ProductSort sort) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof PersonModel person) {
            CustomerModel customer = customerRepository.findByPersonModel(person)
                    .orElseThrow(() -> new AccessDeniedException("Customer profile not found."));

            Optional<OrderModel> optionalOrder = orderRepository.findByCustomerAndStatus(customer, OrderStatus.OPEN);

            if (optionalOrder.isEmpty()) {
                return new CartResponseDTO(List.of(), BigDecimal.ZERO, OrderStatus.OPEN);
            }

            OrderModel openOrder = optionalOrder.get();

            openOrder.getItems().removeIf(item -> item.getQuantity() <= 0);

            updateTotalValue(openOrder);

            List<CartItemDTO> items = openOrder.getItems().stream()
                    .map(CartItemDTO::fromEntity)
                    .sorted(sort.getComparator())
                    .toList();

            int start = Math.min(page * size, items.size());
            int end = Math.min(start + size, items.size());
            List<CartItemDTO> pagedItems = items.subList(start, end);

            return new CartResponseDTO(pagedItems, openOrder.getTotalValue(), openOrder.getStatus());
        }

        return new CartResponseDTO(List.of(), BigDecimal.ZERO, OrderStatus.OPEN);
    }

    @Transactional
    public void removeFromCart(String barcode) {
        OrderModel order = new OrderModel();
        Object principal = personService.getAuthenticatedPerson();
        if (principal instanceof PersonModel person) {
            CustomerModel customer = customerRepository.findByPersonModel(person)
                    .orElseThrow(() -> new AccessDeniedException("Customer profile not found for this user."));
            order.setCustomer(customer);
        } else {
            throw new AccessDeniedException("Only authenticated users can perform this operation");
        }
        CustomerModel customer = customerRepository.findByPersonModel(person)
                .orElseThrow(() -> new AccessDeniedException("Customer profile not found"));

        order = orderRepository.findByCustomerAndStatus(customer, OrderStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("No open order found"));

        ProductModel product = productService.findProductModelByBarcode(barcode);

        order.getItems().removeIf(item -> item.getProduct().equals(product));

        updateTotalValue(order);
    }

    private static CartItemModel getCartItemModel(ProductOrderRequestDTO productOrderRequestDTO, ProductModel productModel, OrderModel order) {
        CartItemModel item = new CartItemModel();
        item.setOrder(order);
        item.setProduct(productModel);

        if (productOrderRequestDTO.quantity() != 0)
            item.setQuantity(productOrderRequestDTO.quantity());
        else
            item.setQuantity(1);

        item.setPrice(productModel.getPrice());
        return item;
    }

    private void updateTotalValue(OrderModel order) {
        BigDecimal total = order.getItems().stream()
                .map(CartItemModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalValue(total);
    }

}
