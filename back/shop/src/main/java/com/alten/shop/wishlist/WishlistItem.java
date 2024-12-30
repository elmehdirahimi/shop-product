package com.alten.shop.wishlist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WishlistItem {
    private Long productId;
    private String productName;
    private String productImage;
    private double price;
    private String category;
    private String inventoryStatus;
} 