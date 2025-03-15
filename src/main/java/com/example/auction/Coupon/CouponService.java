package com.example.auction.Coupon;

import com.example.auction.Coupon.Dto.CouponRequestDto;
import com.example.auction.Coupon.Dto.CouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    /**
     * <p>쿠폰 발행</p>
     * @param longinUserId 로그인유저 식별자
     * @param requestDto {@link CouponRequestDto}
     * @return CouponResponseDto {@link CouponResponseDto}
     */
    public CouponResponseDto issuedCoupon(Long longinUserId , CouponRequestDto requestDto){

        LocalDateTime expiredAt = getExpiredAtFromLocalDate(requestDto.getExpiredAt());
        Coupon coupon = new Coupon(longinUserId , requestDto);
        coupon.setExpiredAt(expiredAt);
        couponRepository.save(coupon);
        return CouponResponseDto.toDto(coupon);
    }


    /**
     * <p>쿠폰 단건 조회</p>
     * @param couponId 쿠폰 식별자
     * @return CouponResponseDto {@link CouponResponseDto}
     */
    public CouponResponseDto getCoupon(Long couponId){
        return CouponResponseDto.toDto(couponRepository.findByIdOrElseThrow(couponId));
    }

    /**
     * <p>관리자 발행 쿠폰 다건 조회</p>
     * @param page 조회페이지 번호 미입력시 defaultValue 설정
     * @param size 조회페이지 크기 미입력시 defaultValue 설정
     * @return Page<CouponResponseDto> {@link CouponResponseDto}
     */
    public Page<CouponResponseDto> getUserCoupon(int page , int size){

        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Coupon> couponPage = couponRepository.findAll(pageable);
        return couponPage.map(coupon -> new CouponResponseDto(
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
        ));
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

    @Transactional
    public void expiredCoupon(){
        couponRepository.expiredCoupon(CouponStatus.EXPIRED);
    }

    public boolean validCoupon(Long couponId){
        Coupon coupon = couponRepository.findByIdOrElseThrow(couponId);
        return coupon.getStatus() != CouponStatus.EXPIRED;
    }


}
