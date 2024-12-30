package com.alten.shop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final  ProductRepository productRepository;
    
    public List<Product> getAll() {
        return productRepository.findAll().stream().map(ProductEntity::toDomain).toList();
    }

    public Product getProduct(Long productId) {
        Optional<ProductEntity> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return  product.get().toDomain();
    }
    
    public Product create(Product product) {
        ProductEntity productEntity = ProductEntity.fromDomain(product);
        productEntity.setId(null);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(productEntity).toDomain();
    }

    
    public Product update(Long productId, Product product) {
        Optional<ProductEntity> existingProduct = productRepository.findById(productId);

        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        ProductEntity updatedProduct = existingProduct.get();
        updatedProduct.setCode(product.getCode());
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setImage(product.getImage());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setQuantity(product.getQuantity());
        updatedProduct.setInternalReference(product.getInternalReference());
        updatedProduct.setShellId(product.getShellId());
        updatedProduct.setInventoryStatus(product.getInventoryStatus());
        updatedProduct.setRating(product.getRating());
        updatedProduct.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(updatedProduct).toDomain();
    }

    
    public boolean delete(Long productId) {
        if (!productRepository.existsById(productId)) {
            return false;
        }
        productRepository.deleteById(productId);
        return true;
    }


}
