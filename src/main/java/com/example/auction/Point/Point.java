package com.example.auction.Point;


import com.example.auction.Auction.Auction;
import com.example.auction.Global.BaseEntity;
import com.example.auction.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "point")
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private Long auctionId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PointReason reason;

    @Column(nullable = false)
    private int usePoint;

    @Column(nullable = false)
    private int totalPoint;

    public Point (){}

    public Point (Long userId , Long auctionId , PointReason reason , int  point , int totalPoint ){
        this.userId = userId;
        this.auctionId = auctionId;
        this.reason = reason;
        this.usePoint = point;
        this.totalPoint = totalPoint;
    }

    public Point (Long userId , PointReason reason , int point , int totalPoint){
        this.userId = userId;
        this.reason = reason;
        this.usePoint = point;
        this.totalPoint = totalPoint;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

}