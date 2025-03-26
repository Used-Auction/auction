package com.example.auction.Point.Dto;

import com.example.auction.Point.Point;
import com.example.auction.Point.PointReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PointResponseDto {

    private final Long id;
    private final Long userId;
    private final Long auctionId;
    private final PointReason reason;
    private final int usePoint;
    private final int totalPoint;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

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
