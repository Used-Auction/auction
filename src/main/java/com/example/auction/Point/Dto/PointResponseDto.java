package com.example.auction.Point.Dto;

import com.example.auction.Point.Point;
import com.example.auction.Point.PointReason;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PointResponseDto {

    private final Long id;

    private final Long userId;

    private final Long auctionId;

    private final PointReason reason;

    private final int usePoint;

    private final int totalPoint;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public PointResponseDto(Long id, Long userId, Long auctionId, PointReason reason, int usePoint, int totalPoint, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.auctionId = auctionId;
        this.reason = reason;
        this.usePoint = usePoint;
        this.totalPoint = totalPoint;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PointResponseDto toDto(Point point){
        return new PointResponseDto(
                point.getId(),
                point.getUserId(),
                point.getAuctionId(),
                point.getReason(),
                point.getUsePoint(),
                point.getTotalPoint(),
                point.getCreatedAt(),
                point.getUpdatedAt()
        );
    }
}
