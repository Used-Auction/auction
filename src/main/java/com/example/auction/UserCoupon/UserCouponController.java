package com.example.auction.UserCoupon;

import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Global.CommonResponseBody;
import com.example.auction.UserCoupon.Dto.UserCouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    @GetMapping("/{userCouponId}")
    public ResponseEntity<CommonResponseBody<UserCouponResponseDto>> getUserCoupon(
            @PathVariable Long userCouponId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("유저쿠폰 단건 조회",
                userCouponService.getCouponRecord(userDetails.getUser().getId(),userCouponId)));
    }

    @PostMapping("/coupons/{couponId}")
    public ResponseEntity<CommonResponseBody<UserCouponResponseDto>> receiveCoupon(
            @PathVariable Long couponId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("쿠폰 발급받기",
                userCouponService.receiveCoupon(userDetails.getUser().getId(),couponId)));
    }
}
