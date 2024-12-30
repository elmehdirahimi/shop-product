package com.alten.shop.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_accounts")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String firstname;
    
    @Column(unique = true)
    private String email;
    
    private String password;

    public Account toDomain() {
        return new Account(id, username, firstname, email, password);
    }

    public static AccountEntity fromDomain(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setUsername(account.getUsername());
        entity.setFirstname(account.getFirstname());
        entity.setEmail(account.getEmail());
        entity.setPassword(account.getPassword());
        return entity;
    }
}