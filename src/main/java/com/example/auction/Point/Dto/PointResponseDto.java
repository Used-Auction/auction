package com.example.auction.Point.Dto;

import com.example.auction.Point.PointReason;
import lombok.Getter;

@Getter
public class PointResponseDto {

    private final Long id;

    private final Long userId;

    private final Long auctionId;

    private final PointReason reason;

    private final int usePoint;

    private final int totalPoint;

    public PointResponseDto(Long id, Long userId, Long auctionId, PointReason reason, int usePoint, int totalPoint) {
        this.id = id;
        this.userId = userId;
        this.auctionId = auctionId;
        this.reason = reason;
        this.usePoint = usePoint;
        this.totalPoint = totalPoint;
    }
}
