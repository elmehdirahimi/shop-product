package com.alten.shop.wishlist;

import com.alten.shop.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    Optional<WishlistEntity> findByAccount(AccountEntity account);
} 