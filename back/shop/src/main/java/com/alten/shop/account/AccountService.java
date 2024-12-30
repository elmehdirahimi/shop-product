package com.alten.shop.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Account createAccount(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        AccountEntity accountEntity = AccountEntity.fromDomain(account);
        accountEntity.setPassword(passwordEncoder.encode(account.getPassword()));
        
        return accountRepository.save(accountEntity).toDomain();
    }

    public Account authenticate(String email, String password) {
        AccountEntity accountEntity = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (passwordEncoder.matches(password, accountEntity.getPassword())) {
            return accountEntity.toDomain();
        }

        throw new RuntimeException("Invalid credentials");
    }

    public boolean isAdmin(String email) {
        return "admin@admin.com".equals(email);
    }

    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(AccountEntity::toDomain)
                .orElse(null);
    }
}
