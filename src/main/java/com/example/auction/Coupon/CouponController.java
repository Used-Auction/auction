package com.example.auction.Coupon;

import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Coupon.Dto.CouponRequestDto;
import com.example.auction.Coupon.Dto.CouponResponseDto;
import com.example.auction.UserCoupon.UserCouponService;
import com.example.auction.UserCoupon.Dto.UserCouponResponseDto;
import com.example.auction.Global.CommonResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final UserCouponService userCouponService;

    /**
     * <p>쿠폰 발행</p>
     * @param requestDto 쿠폰 요청 Dto {@link CouponRequestDto}
     * @param userDetails 유저 Principal 객체
     * @return CouponResponseDto 쿠폰 응답 Dto {@link CouponResponseDto}
     */
    @PostMapping("/issue")
    public ResponseEntity<CommonResponseBody<CouponResponseDto>> issuedCoupon(
            @RequestBody CouponRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("쿠폰 발행",
                couponService.issuedCoupon(userDetails.getUser().getId(),requestDto)));
    }

    /**
     * <p>쿠폰 단건 조회</p>
     * @param couponId 쿠폰 식별자
     * @return CouponResponseDto 쿠폰 응답 Dto {@link CouponResponseDto}
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<CommonResponseBody<CouponResponseDto>> getCoupon(
            @PathVariable Long couponId
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("쿠폰 단건 조회",
                couponService.getCoupon(couponId)));
    }

    /**
     * <p>관리자 발행쿠폰 리스트 조회</p>
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @return Page<CouponResponseDto>
     */
    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<Page<CouponResponseDto>>> getUserCoupon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("관리자 발행쿠폰 리스트 조회",
                couponService.getUserCoupon(page,size)));
    }








}
