package com.example.auction.UserCoupon.Dto;

import com.example.auction.UserCoupon.UserCoupon;
import com.example.auction.UserCoupon.UserCouponStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCouponResponseDto {

    private final Long id;

    private final Long userId;

    private final Long couponId;

    private final UserCouponStatus status;

    private final LocalDateTime usedAt;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public UserCouponResponseDto(Long id, Long userId, Long couponId, UserCouponStatus status, LocalDateTime usedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.status = status;
        this.usedAt = usedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
