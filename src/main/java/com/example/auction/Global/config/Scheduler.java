package com.example.auction.Global.config;

import com.example.auction.Auction.AuctionService;
import com.example.auction.Coupon.CouponService;
import com.example.auction.UserCoupon.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final AuctionService auctionService;
    private final CouponService couponService;
    private final UserCouponService userCouponService;

    //  (cron = "0 0 00 * * *") 매일 00시
    //  (cron = "0 0 00 * * *") 3초마다
    @Scheduled(cron = "0/3 * * * * ?")
    public void statusUpdate(){
        auctionService.expiredAuction();
        couponService.expiredCoupon();
        userCouponService.expiredCouponRecord();
    }
}
