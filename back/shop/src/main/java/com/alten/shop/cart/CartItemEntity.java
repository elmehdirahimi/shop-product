package com.alten.shop.cart;

import com.alten.shop.product.ProductEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_cart_items")
@Getter
@Setter
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductEntity product;

    private Integer quantity;
} 