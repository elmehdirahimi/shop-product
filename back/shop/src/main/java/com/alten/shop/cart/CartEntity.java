package com.alten.shop.cart;

import com.alten.shop.account.AccountEntity;
import com.alten.shop.product.ProductEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_carts")
@Getter
@Setter
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AccountEntity account;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItemEntity> items = new HashSet<>();
} 