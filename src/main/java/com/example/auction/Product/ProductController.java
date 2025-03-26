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

    /**
     * <p>상품 등록</p>
     * @param requestDto 상품 요청 Dto {@link ProductRequestDto}
     * @param userDetails 로그인유저 Principal 객체
     * @return ProductResponseDto 상품 응답 Dto {@link ProductResponseDto}
     */
    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> addProduct(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProductRequestDto requestDto
            ){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 등록",
                        productService.addProduct(userDetails.getUser().getId(),requestDto)));
    }

    /**
     * <p>상품 조회</p>
     * @param productId 상품 식별자
     * @return ProductResponseDto 상품 응답 Dto {@link ProductResponseDto}
     */
    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> getProduct(@PathVariable Long productId){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 조회"
                        , productService.getProduct(productId)));
    }

    /**
     * <p>상품 수정</p>
     * @param productId 상품 식별자
     * @param requestDto 상품 요청 Dto {@link ProductRequestDto}
     * @return ProductResponseDto 상품 응답 Dto {@link ProductResponseDto}
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto requestDto){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 수정" ,
                        productService.updateProduct(productId,requestDto)));
    }

    /**
     * <p>상품 비활성화</p>
     * @param productId 상품 식별자
     * @return ProductResponseDto 상품 응답 Dto {@link ProductResponseDto}
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<ProductResponseDto>> deleteProduct(
            @PathVariable Long productId){
        return ResponseEntity.ok().
                body(new CommonResponseBody<>("상품 비활성화" ,
                        productService.deleteProduct(productId)));

    }

}
