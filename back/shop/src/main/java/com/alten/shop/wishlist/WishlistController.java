package com.alten.shop.wishlist;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/products/{productId}")
    public void addProduct(@PathVariable Long productId, Authentication authentication) {
        wishlistService.addProduct(authentication.getName(), productId);
    }

    @DeleteMapping("/products/{productId}")
    public void removeProduct(@PathVariable Long productId, Authentication authentication) {
        wishlistService.removeProduct(authentication.getName(), productId);
    }

    @GetMapping
    public Wishlist getWishlist(Authentication authentication) {
        return wishlistService.getWishlist(authentication.getName());
    }
} 