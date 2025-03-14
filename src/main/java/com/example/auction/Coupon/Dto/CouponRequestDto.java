package com.example.auction.Coupon.Dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponRequestDto {

    private String name;

    private String image;

    private int amount;

    private int discountAmount;

    private LocalDate expiredAt;

}
