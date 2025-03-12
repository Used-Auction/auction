package com.example.auction.Product;

import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Global.CommonResponseBody;
import com.example.auction.Product.Dto.ProductRequestDto;
import com.example.auction.Product.Dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> addProduct(
            @RequestBody ProductRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 등록",
                        productService.addProduct(userDetails.getUser().getId(),requestDto)));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> getProduct(@PathVariable Long productId){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 조회"
                        , productService.getProduct(productId)));
    }
    
    @PatchMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto requestDto){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 수정" ,
                        productService.updateProduct(productId,requestDto)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> deleteProduct(
            @PathVariable Long productId){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 비활성화" ,
                        productService.deleteProduct(productId)));

    }

}
