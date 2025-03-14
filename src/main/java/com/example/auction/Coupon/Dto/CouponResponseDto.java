package com.example.auction.Coupon.Dto;

import com.example.auction.Coupon.Coupon;
import com.example.auction.Coupon.CouponStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponResponseDto  {

    private final Long id;

    private final Long userId;

    private final String name;

    private final String image;

    private final int amount;

    private final int discountAmount;

    private final CouponStatus status;

    private final LocalDateTime expiredAt;

    private final LocalDateTime updatedAt;

    private final LocalDateTime createdAt;

    public CouponResponseDto(Long id, Long userId, String name, String image, int amount, int discountAmount, CouponStatus status, LocalDateTime expiredAt, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.status = status;
        this.expiredAt = expiredAt;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public static CouponResponseDto toDto(Coupon coupon){
        return new CouponResponseDto(
                coupon.getId(),
                coupon.getUserId(),
                coupon.getName(),
                coupon.getImage(),
                coupon.getAmount(),
                coupon.getDiscountAmount(),
                coupon.getStatus(),
                coupon.getExpiredAt(),
                coupon.getUpdatedAt(),
                coupon.getCreatedAt()
        );
    }
}
