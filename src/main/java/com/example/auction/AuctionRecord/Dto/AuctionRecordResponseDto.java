package com.example.auction.AuctionRecord.Dto;

import com.example.auction.AuctionRecord.AuctionRecord;
import com.example.auction.AuctionRecord.AuctionRecordStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuctionRecordResponseDto {

    private final Long id;

    private final Long userId;

    private final Long auctionId;

    private final AuctionRecordStatus status;

    private final int bidPoint;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static AuctionRecordResponseDto toDto(AuctionRecord auctionRecord){
        return new AuctionRecordResponseDto(
                auctionRecord.getId(),
                auctionRecord.getUserId(),
                auctionRecord.getAuctionId(),
                auctionRecord.getStatus(),
                auctionRecord.getBidPoint(),
                auctionRecord.getCreatedAt(),
                auctionRecord.getUpdatedAt()
        );
    }
}
