package com.healthPharmacy.demo.model;

import jakarta.persistence.*;

@Entity
public class ShoppingCartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProductModel produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private OrderItemModel pedido;

    private int quantidade;
    private double preco;
}