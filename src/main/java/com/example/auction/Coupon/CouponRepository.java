package com.example.auction.Coupon;

import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon,Long> {

    default Coupon findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CustomException(ErrorCode.RESOURCES_NOT_FOUND));
    }

    Page<Coupon> findAllByUserId(Long userId, Pageable pageable);

    @Modifying
    @Query(value = "update Coupon c set c.status = :status where c.expiredAt < now()")
    void expiredCoupon(@Param("status") CouponStatus status);

}
