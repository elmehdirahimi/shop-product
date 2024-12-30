package com.alten.shop.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Cart {
    private Long id;
    private List<CartItem> items = new ArrayList<>();
    private double total;

    public double calculateTotal() {
        total = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        return total;
    }
} 