package com.example.auction.Product;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.Product.Dto.ProductRequestDto;
import com.example.auction.Product.Dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> addProduct(
            @Valid @RequestBody ProductRequestDto requestDto
            ){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 등록"
                , productService.addProduct(1L,requestDto)));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> getProduct(@PathVariable Long productId){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 조회"
                        , productService.getProduct(productId)));
        return ResponseEntity.ok().body(new CommonResponseBody<>("제품 등록" , productService.addProduct(1L,requestDto)));
    }
}
