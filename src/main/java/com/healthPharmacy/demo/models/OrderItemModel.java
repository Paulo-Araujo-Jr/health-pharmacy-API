package com.healthPharmacy.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Purchase")
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    private CustomerModel customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ShoppingCart_id")
    private List<ShoppingCartModel> goods = new ArrayList<>();

    private LocalDateTime orderDate;
    @Column(name = "total_value")
    private double totalValue;


}