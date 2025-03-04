package com.example.auction.Product.Dto;

import com.example.auction.Product.Product;
import com.example.auction.Product.ProductStatus;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private final Long id;

    private final Long userId;

    private final String name;

    private final String content;

    private final String image;

    private final ProductStatus status;

    public ProductResponseDto(Long id, Long userId, String name, String content, String image, ProductStatus status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.content = content;
        this.image = image;
        this.status = status;
    }

    public static ProductResponseDto toDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getUserId(),
                product.getName(),
                product.getContent(),
                product.getImage(),
                product.getProductStatus()
        );
    }
}
