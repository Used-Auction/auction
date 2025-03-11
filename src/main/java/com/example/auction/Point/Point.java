package com.example.auction.Point;


import com.example.auction.Auction.Auction;
import com.example.auction.Global.BaseEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PointReason reason;

    @Column(nullable = false)
    private int usePoint;

    @Column(nullable = false)
    private int totalPoint;

    public Point (){}

    public Point (Long userId , Auction auction , PointReason reason , int  point , int totalPoint ){
        this.userId = userId;
        this.auction = auction;
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

}