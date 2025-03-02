package com.example.auction.Product;

import com.example.auction.Product.Dto.ProductRequestDto;
import com.example.auction.Product.Dto.ProductResponseDto;
import com.example.auction.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto addProduct (Long loginUserId , ProductRequestDto requestDto){
        Product product = new Product(loginUserId , requestDto);
        Product saveProduct = productRepository.save(product);
        return ProductResponseDto.toDto(saveProduct);
    }
}
