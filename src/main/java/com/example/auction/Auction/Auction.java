package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Global.BaseEntity;
import com.example.auction.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "auction")
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(nullable = false)
    private int minPoint;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status = AuctionStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public Auction (){}

    public Auction (Long userId , Product product , AuctionRequestDto requestDto){
        this.userId = userId;
        this.product = product;
        this.minPoint = requestDto.getMinPoint();
        this.expiredAt = requestDto.getExpiredAt();
    }

    public void updateAuction(AuctionRequestDto requestDto){
        this.minPoint = requestDto.getMinPoint();
        this.expiredAt = requestDto.getExpiredAt();
    }

    public void expiredAuction(){
        this.status = AuctionStatus.EXPIRED;
    }



}
