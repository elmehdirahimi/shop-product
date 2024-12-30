package com.alten.shop.cart;

import com.alten.shop.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByAccount(AccountEntity account);
} 