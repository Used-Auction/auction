package com.example.auction.UserCoupon;

import com.example.auction.Global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "coupon_record")
public class UserCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private UserCouponStatus status = UserCouponStatus.AVAILABLE;

    private LocalDateTime usedAt;

    public UserCoupon(){}

    public UserCoupon(Long userId , Long couponId){
        this.userId = userId;
        this.couponId = couponId;
    }

    public void setUsedAt(LocalDateTime usedAt){
        this.usedAt = usedAt;
    }
}
