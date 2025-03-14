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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @PostMapping("/issue")
    public ResponseEntity<CommonResponseBody<CouponResponseDto>> issuedCoupon(
            @RequestBody CouponRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("쿠폰 발행",
                couponService.issuedCoupon(userDetails.getUser().getId(),requestDto)));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CommonResponseBody<CouponResponseDto>> getCoupon(
            @PathVariable Long couponId
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("쿠폰 단건 조회",
                couponService.getCoupon(couponId)));
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<Page<CouponResponseDto>>> getUserCoupon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("관리자 발행쿠폰 리스트 조회",
                couponService.getUserCoupon(userDetails.getUser().getId(),page,size)));
    }








}
