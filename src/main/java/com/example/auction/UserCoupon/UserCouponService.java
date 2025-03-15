package com.example.auction.UserCoupon;


import com.example.auction.Coupon.CouponService;
import com.example.auction.UserCoupon.Dto.UserCouponResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponService couponService;


    public UserCouponResponseDto receiveCoupon(Long userId , Long couponId){

        if (couponService.validCoupon(couponId)){
            UserCoupon userCoupon = new UserCoupon(userId,couponId);
            userCouponRepository.save(userCoupon);
            return UserCouponResponseDto.toDto(userCoupon);
        }else {
            throw new CustomException(ErrorCode.EXPIRED_COUPON);
        }
    }

    public UserCouponResponseDto getCouponRecord(Long userId , Long couponRecordId){
       UserCoupon findUserCoupon = userCouponRepository.findByIdOrElseThrow(couponRecordId);
       if (!Objects.equals(userId, findUserCoupon.getUserId())){
           throw new CustomException(ErrorCode.FORBIDDEN_ERROR);
       }
       return UserCouponResponseDto.toDto(findUserCoupon);
    }


    @Transactional
    public void expiredCouponRecord(){
        userCouponRepository.expiredCouponRecord(UserCouponStatus.EXPIRED);
    }

}
