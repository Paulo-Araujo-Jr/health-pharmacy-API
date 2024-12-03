package com.healthPharmacy.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class ShoppingCartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private OrderItemModel request;

    private int quantity;
    private double price;

}