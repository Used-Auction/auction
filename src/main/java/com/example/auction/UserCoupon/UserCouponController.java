package com.example.auction.UserCoupon;

import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Global.CommonResponseBody;
import com.example.auction.UserCoupon.Dto.UserCouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    /**
     * <p>유저쿠폰 단건 조회</p>
     * @param userCouponId 조회할 유저쿠폰식별자
     * @param userDetails 로그인유저 Principal 객체 {@link UserDetailsImpl}
     * @return UserCouponResponseDto {@link UserCouponResponseDto}
     */
    @GetMapping("/{userCouponId}")
    public ResponseEntity<CommonResponseBody<UserCouponResponseDto>> getUserCoupon(
            @PathVariable Long userCouponId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("유저쿠폰 단건 조회",
                userCouponService.getUserCoupon(userDetails.getUser().getId(),userCouponId)));
    }

    /**
     * <p>유저쿠폰 리스트 조회</p>
     * @param userDetails 로그인유저 Principal 객체 {@link UserDetailsImpl}
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @return Page<UserCouponResponseDto> {@link UserCouponResponseDto}
     */
    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<Page<UserCouponResponseDto>>> getUserCouponList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok().body(new CommonResponseBody<>("유저쿠폰 리스트 조회",
                userCouponService.getUserCouponList(userDetails.getUser().getId(), page,size)));
    }

    /**
     * <p>쿠폰 발급받기</p>
     * @param couponId 발급받을 쿠폰식별자
     * @param userDetails userDetails 로그인유저 Principal 객체 {@link UserDetailsImpl}
     * @return UserCouponResponseDto {@link UserCouponResponseDto}
     */
    @PostMapping("/coupons/{couponId}")
    public ResponseEntity<CommonResponseBody<UserCouponResponseDto>> receiveCoupon(
            @PathVariable Long couponId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("쿠폰 발급받기",
                userCouponService.receiveCoupon(userDetails.getUser().getId(),couponId)));
    }
}
