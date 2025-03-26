package com.example.auction.Product.Dto;

import com.example.auction.Product.Product;
import com.example.auction.Product.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ProductResponseDto {

    private final Long id;

    private final Long userId;

    private final String name;

    private final String content;

    private final String image;

    private final ProductStatus status;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static ProductResponseDto toDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getUserId(),
                product.getName(),
                product.getContent(),
                product.getImage(),
                product.getProductStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
