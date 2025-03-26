package com.example.auction.Product.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequestDto {

    private final String name;

    private final String content;

    private final String image;

}
