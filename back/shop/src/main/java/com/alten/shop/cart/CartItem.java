package com.alten.shop.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    private Long id;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
} 