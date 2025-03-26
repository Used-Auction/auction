package com.example.auction.UserCoupon.Dto;

import com.example.auction.UserCoupon.UserCoupon;
import com.example.auction.UserCoupon.UserCouponStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserCouponResponseDto {

    private final Long id;
    private final Long userId;
    private final Long couponId;
    private final UserCouponStatus status;
    private final LocalDateTime usedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserCouponResponseDto toDto(UserCoupon userCoupon){
        return new UserCouponResponseDto(
                userCoupon.getId(),
                userCoupon.getUserId(),
                userCoupon.getCouponId(),
                userCoupon.getStatus(),
                userCoupon.getUsedAt(),
                userCoupon.getCreatedAt(),
                userCoupon.getUpdatedAt()
        );
    }

}
