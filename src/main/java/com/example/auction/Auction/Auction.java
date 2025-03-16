package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "auction")
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int minPoint;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status = AuctionStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public Auction (Long userId , Long productId , AuctionRequestDto requestDto , LocalDateTime expiredAt){
        this.userId = userId;
        this.productId = productId;
        this.minPoint = requestDto.getMinPoint();
        this.expiredAt = expiredAt;
    }

    public Auction (Long userId , Long productId , int minPoint , LocalDateTime expiredAt){
        this.userId = userId;
        this.productId = productId;
        this.minPoint = minPoint;
        this.expiredAt = expiredAt;
    }

    public void updateAuction(AuctionRequestDto requestDto , LocalDateTime expiredAt){
        this.minPoint = requestDto.getMinPoint();
        this.expiredAt = expiredAt;
    }

    public void expiredAuction(){
        this.status = AuctionStatus.EXPIRED;
    }



}
