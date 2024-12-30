package com.alten.shop.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public void addItem(@RequestBody CartItem item, Authentication authentication) {
        cartService.addItem(authentication.getName(), item);
    }

    @DeleteMapping("/items/{productId}")
    public void removeItem(@PathVariable Long productId, Authentication authentication) {
        cartService.removeItem(authentication.getName(), productId);
    }

    @GetMapping
    public Cart getCart(Authentication authentication) {
        return cartService.getCart(authentication.getName());
    }
} 