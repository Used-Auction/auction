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

    /**
     *
     * @param loginUserId 로그인 유저 식별자
     * @param requestDto 요청 Dto
     * @return ProductResponseDto
     */
    public ProductResponseDto addProduct (Long loginUserId , ProductRequestDto requestDto){
        Product product = new Product(loginUserId , requestDto);
        Product saveProduct = productRepository.save(product);
        return ProductResponseDto.toDto(saveProduct);
    }

    /**
     *
     * @param productId 상품식별자
     * @return ProductResponseDto
     */
    public ProductResponseDto getProduct (Long productId){
        Product product = productRepository.findByIdOrElseThrow(productId);
        return ProductResponseDto.toDto(product);
    }
}
