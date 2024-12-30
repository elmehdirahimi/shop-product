package com.alten.shop.wishlist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Wishlist {
    private Long id;
    private List<WishlistItem> items = new ArrayList<>();
} 