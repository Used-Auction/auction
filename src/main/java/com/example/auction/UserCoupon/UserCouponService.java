package com.example.auction.UserCoupon;


import com.example.auction.Coupon.CouponService;
import com.example.auction.UserCoupon.Dto.UserCouponResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponService couponService;

    /**
     * <p>쿠폰 발급 받기</p>
     * @param userId 발급받을 유저식별자
     * @param couponId 발급받을 쿠폰식별자
     * @return UserCouponResponseDto {@link UserCouponResponseDto}
     */
    public UserCouponResponseDto receiveCoupon(Long userId , Long couponId){
        //해당쿠폰 이용가능한지 확인
        if (couponService.validCoupon(couponId)){
            //해당쿠폰 발급받았는지 확인
            if (!existUserCoupon(couponId)){
                UserCoupon userCoupon = new UserCoupon(userId,couponId);
                userCouponRepository.save(userCoupon);
                return UserCouponResponseDto.toDto(userCoupon);
            }
            throw new CustomException(ErrorCode.EXIST_COUPON);
        }else {
            throw new CustomException(ErrorCode.EXPIRED_COUPON);
        }
    }

    /**
     * <p>유저 쿠폰 단건 조회</p>
     * @param userId 해당 유저식별자
     * @param userCouponId 조회할 유저쿠폰식별자
     * @return UserCouponResponseDto {@link UserCouponResponseDto}
     */
    public UserCouponResponseDto getUserCoupon(Long userId , Long userCouponId){
       UserCoupon findUserCoupon = userCouponRepository.findByIdOrElseThrow(userCouponId);
       if (!Objects.equals(userId, findUserCoupon.getUserId())){
           throw new CustomException(ErrorCode.FORBIDDEN_ERROR);
       }
       return UserCouponResponseDto.toDto(findUserCoupon);
    }

    /**
     * <p>유저 쿠폰 리스트 조회</p>
     * @param userId 해당 유저 식별자
     * @param page 조회할 페이지 번호
     * @param size 조회할 페이지 크기
     * @return Page<UserCouponResponseDto> {@link UserCouponResponseDto}
     */
    public Page<UserCouponResponseDto> getUserCouponList(Long userId , int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserCoupon> userCouponPage = userCouponRepository.findByUserId(userId,pageable);
        return userCouponPage.map(UserCouponResponseDto::toDto);
    }

    @Transactional
    public void expiredCouponRecord(){
        userCouponRepository.expiredCouponRecord(UserCouponStatus.EXPIRED);
    }

    private boolean existUserCoupon(Long couponId){
        return userCouponRepository.existsByCouponId(couponId);
    }

}
