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
     * 상품 등록
     * @param loginUserId 로그인 유저 식별자
     * @param requestDto {@link ProductRequestDto}
     * @return ProductResponseDto {@link ProductResponseDto}
     */
    public ProductResponseDto addProduct (Long loginUserId , ProductRequestDto requestDto){
        Product product = new Product(loginUserId , requestDto);
        Product saveProduct = productRepository.save(product);
        return ProductResponseDto.toDto(saveProduct);
    }
    /**
     * 상품 단건조회
     * @param productId 상품식별자
     * @return ProductResponseDto {@link ProductResponseDto}
     */
    public ProductResponseDto getProduct (Long productId){
        Product product = productRepository.findByIdOrElseThrow(productId);
        return ProductResponseDto.toDto(product);
    }

    /**
     * 상품 수정
     * @param productId 상품식별자
     * @param requestDto {@link ProductRequestDto}
     * @return ProductResponseDto {@link ProductResponseDto}
     */
    public ProductResponseDto updateProduct (Long productId , ProductRequestDto requestDto ){
        Product findProduct = productRepository.findByIdOrElseThrow(productId);
        findProduct.updateProduct(requestDto);
        productRepository.save(findProduct);
        return ProductResponseDto.toDto(findProduct);
    }

    /**
     *
     * @param productId 상품식별자
     * @return ProductResponseDto {@link ProductResponseDto}
     */
    public ProductResponseDto deleteProduct (Long productId){
        Product findProduct = productRepository.findByIdOrElseThrow(productId);
        findProduct.deleteProduct();
        productRepository.save(findProduct);
        return ProductResponseDto.toDto(findProduct);
    }

}
