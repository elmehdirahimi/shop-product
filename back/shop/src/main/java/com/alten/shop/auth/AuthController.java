package com.alten.shop.auth;

import com.alten.shop.account.Account;
import com.alten.shop.account.AccountService;
import com.alten.shop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) {
        Account account = accountService.authenticate(authRequest.getEmail(), authRequest.getPassword());
        if (account != null) {
            String token = jwtUtil.generateToken(account.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
} 