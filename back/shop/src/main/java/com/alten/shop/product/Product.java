package com.alten.shop.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Product {

    private Long id;

    private String code;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    private String image;

    @NotNull
    @NotBlank
    private String category;
    @NotNull
    private Double price;
    @NotNull
    private Integer quantity;

    private String internalReference;
    @NotNull
    private Long shellId;

    @NotNull
    private InventoryStatus inventoryStatus;
    @NotNull
    private Double rating;

    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedAt;

    public enum InventoryStatus {
        INSTOCK , LOWSTOCK , OUTOFSTOCK
    }
}
