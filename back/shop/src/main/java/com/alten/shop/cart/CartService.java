package com.alten.shop.cart;

import com.alten.shop.account.AccountEntity;
import com.alten.shop.account.AccountRepository;
import com.alten.shop.product.ProductEntity;
import com.alten.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addItem(String email, CartItem item) {
        AccountEntity account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        CartEntity cart = cartRepository.findByAccount(account)
            .orElseGet(() -> {
                CartEntity newCart = new CartEntity();
                newCart.setAccount(account);
                return newCart;
            });

        ProductEntity product = productRepository.findById(item.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setProduct(product);
        cartItem.setQuantity(item.getQuantity());
        cart.getItems().add(cartItem);

        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(String email, Long productId) {
        AccountEntity account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        CartEntity cart = cartRepository.findByAccount(account)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public Cart getCart(String email) {
        AccountEntity account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        return cartRepository.findByAccount(account)
            .map(this::mapToDto)
            .orElse(new Cart());
    }

    private Cart mapToDto(CartEntity entity) {
        Cart cart = new Cart();
        cart.setId(entity.getId());
        cart.setItems(entity.getItems().stream()
            .map(this::mapToItemDto)
            .toList());
        return cart;
    }

    private CartItem mapToItemDto(CartItemEntity entity) {
        CartItem item = new CartItem();
        item.setId(entity.getId());
        item.setProductId(entity.getProduct().getId());
        item.setQuantity(entity.getQuantity());
        return item;
    }
} 