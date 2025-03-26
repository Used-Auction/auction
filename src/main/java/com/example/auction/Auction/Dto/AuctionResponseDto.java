package com.example.auction.Auction.Dto;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuctionResponseDto {

    private final Long id;

    private final Long userId;

    private final Long productId;

    private final int minPoint;

    private final AuctionStatus status;

    private final LocalDateTime expiredAt;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static AuctionResponseDto toDto(Auction auction){
        return new AuctionResponseDto(
                auction.getId(),
                auction.getUserId(),
                auction.getProductId(),
                auction.getMinPoint(),
                auction.getStatus(),
                auction.getExpiredAt(),
                auction.getCreatedAt(),
                auction.getUpdatedAt()
        );
    }
}
