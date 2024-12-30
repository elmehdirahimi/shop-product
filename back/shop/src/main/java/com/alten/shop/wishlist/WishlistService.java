package com.alten.shop.wishlist;

import com.alten.shop.account.AccountEntity;
import com.alten.shop.account.AccountRepository;
import com.alten.shop.product.ProductEntity;
import com.alten.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addProduct(String email, Long productId) {
        AccountEntity account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        WishlistEntity wishlist = wishlistRepository.findByAccount(account)
            .orElseGet(() -> {
                WishlistEntity newWishlist = new WishlistEntity();
                newWishlist.setAccount(account);
                return newWishlist;
            });

        ProductEntity product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeProduct(String email, Long productId) {
        AccountEntity account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        WishlistEntity wishlist = wishlistRepository.findByAccount(account)
            .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        wishlist.getProducts().removeIf(product -> product.getId().equals(productId));
        wishlistRepository.save(wishlist);
    }

    @Transactional(readOnly = true)
    public Wishlist getWishlist(String email) {
        AccountEntity account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        return wishlistRepository.findByAccount(account)
            .map(this::mapToDto)
            .orElse(new Wishlist());
    }

    private Wishlist mapToDto(WishlistEntity entity) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(entity.getId());
        wishlist.setItems(entity.getProducts().stream()
            .map(this::mapToWishlistItem)
            .toList());
        return wishlist;
    }

    private WishlistItem mapToWishlistItem(ProductEntity product) {
        WishlistItem item = new WishlistItem();
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getImage());
        item.setPrice(product.getPrice());
        item.setCategory(product.getCategory());
        item.setInventoryStatus(product.getInventoryStatus().name());
        return item;
    }
} 