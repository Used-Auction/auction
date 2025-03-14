package com.example.auction.Coupon;

import com.example.auction.Coupon.Dto.CouponRequestDto;
import com.example.auction.Coupon.Dto.CouponResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;



    public CouponResponseDto issuedCoupon(Long longinUserId , CouponRequestDto requestDto){

        LocalDateTime expiredAt = getExpiredAtFromLocalDate(requestDto.getExpiredAt());
        Coupon coupon = new Coupon(longinUserId , requestDto);
        coupon.setExpiredAt(expiredAt);
        couponRepository.save(coupon);
        return CouponResponseDto.toDto(coupon);
    }


    /**
     * 매일 23:59:59 scheduler 동작을 위한 LocalDate -> LocalDateTime 변환
     * @param expiredAt 쿠폰 만료기한
     * @return LocalDateTime
     */
    public LocalDateTime getExpiredAtFromLocalDate(LocalDate expiredAt){
        String expiredAtSt = expiredAt.toString();
        return LocalDateTime.parse(expiredAtSt+" 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
