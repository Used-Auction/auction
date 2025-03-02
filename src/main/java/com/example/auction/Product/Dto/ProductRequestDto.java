package com.example.auction.Product.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRequestDto {

    @NotBlank(message = "제품명을 입력해주세요.")
    private String name;

    @NotBlank(message = "제품 설명을 입력해주세요.")
    private String content;

    private String image;
}
