package com.alten.shop.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private long shellId;

    @Enumerated(EnumType.STRING)
    private Product.InventoryStatus inventoryStatus;

    private double rating;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Product toDomain(){
        return new Product(id, code, name, description,image,category,price,  quantity, internalReference,shellId,inventoryStatus ,rating,createdAt ,updatedAt);
    }

    public static ProductEntity fromDomain(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCode(product.getCode());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setImage(product.getImage());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setInternalReference(product.getInternalReference());
        productEntity.setShellId(product.getShellId());
        productEntity.setInventoryStatus(product.getInventoryStatus());
        productEntity.setRating(product.getRating());
        productEntity.setCreatedAt(product.getCreatedAt());
        productEntity.setUpdatedAt(product.getUpdatedAt());
        return productEntity;
    }

}
