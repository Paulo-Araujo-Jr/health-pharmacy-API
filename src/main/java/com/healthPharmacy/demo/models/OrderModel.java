package com.healthPharmacy.demo.models;

import com.healthPharmacy.demo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Purchase")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    private CustomerModel customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemModel> items = new ArrayList<>();


    private LocalDateTime orderDate;
    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}