package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.ProductOrderRequestDTO;
import com.healthPharmacy.demo.enums.OrderStatus;
import com.healthPharmacy.demo.models.*;
import com.healthPharmacy.demo.repository.CustomerRepository;
import com.healthPharmacy.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,  ProductService productService,  CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void buyNow(ProductOrderRequestDTO productOrderRequestDTO){
        OrderModel order = new OrderModel();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

        CartItemModel item = getCartItemModel(request, productModel, order);
        order.getItems().add(item);
        order.setTotalValue(order.getItems().stream()
                .map(CartItemModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private static CartItemModel getCartItemModel(ProductOrderRequestDTO productOrderRequestDTO, ProductModel productModel, OrderModel order) {
        CartItemModel item = new CartItemModel();
        item.setOrder(order);
        item.setProduct(productModel);

        if (productOrderRequestDTO.quantity() != null)
            item.setQuantity(productOrderRequestDTO.quantity());
        else
            item.setQuantity(1);

        item.setPrice(productModel.getPrice());
        return item;
    }
}
