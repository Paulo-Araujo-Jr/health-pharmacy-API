package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.BuyNowRequestDTO;
import com.healthPharmacy.demo.dto.ProductDTO;
import com.healthPharmacy.demo.enums.OrderStatus;
import com.healthPharmacy.demo.models.*;
import com.healthPharmacy.demo.repository.CustomerRepository;
import com.healthPharmacy.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public void buyNow(BuyNowRequestDTO buyNowRequestDTO){
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

        ProductModel productModel = productService.findProductModelByBarcode(buyNowRequestDTO.productBarcode());
        productService.haveInStock(buyNowRequestDTO.productBarcode(),  buyNowRequestDTO.quantity());
        CartItemModel item = getCartItemModel(buyNowRequestDTO, productModel, order);


        order.setItems(List.of(item));
        order.setTotalValue(item.getPrice());

        productService.productPurchased(buyNowRequestDTO.productBarcode(), item.getQuantity());

        orderRepository.save(order);
    }
    private static CartItemModel getCartItemModel(BuyNowRequestDTO buyNowRequestDTO, ProductModel productModel, OrderModel order) {
        CartItemModel item = new CartItemModel();
        item.setOrder(order);
        item.setProduct(productModel);

        if (buyNowRequestDTO.quantity() != null)
            item.setQuantity(buyNowRequestDTO.quantity());
        else
            item.setQuantity(1);

        item.setPrice(productModel.getPrice());
        return item;
    }
}
