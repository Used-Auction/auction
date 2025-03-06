package com.example.auction.Product.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRequestDto {

    private String name;

    private String content;

    private String image;
}
