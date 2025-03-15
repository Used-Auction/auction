package com.example.auction.UserCoupon;

import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    default UserCoupon findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CustomException(ErrorCode.RESOURCES_NOT_FOUND));
    }

    @Modifying
    @Query(value = "update UserCoupon cr set cr.status = :status where cr.couponId in " +
            "(select c.id from Coupon c where c.expiredAt < now())")
    void expiredCouponRecord(@Param("status") UserCouponStatus status);

    Page<UserCoupon> findByUserId(Long userId, Pageable pageable);

    boolean existsByCouponId(Long couponId);
}
