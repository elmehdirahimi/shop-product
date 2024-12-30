package com.alten.shop.wishlist;

import com.alten.shop.account.AccountEntity;
import com.alten.shop.product.ProductEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_wishlists")
@Getter
@Setter
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AccountEntity account;

    @ManyToMany
    private Set<ProductEntity> products = new HashSet<>();
} 