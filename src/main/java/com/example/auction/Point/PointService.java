package com.example.auction.Point;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Point.Dto.PointEarnResponseDto;
import com.example.auction.Point.Dto.PointResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final AuctionRepository auctionRepository;

    public PointResponseDto bidPoint (Long loginUserId , Long auctionId , int usePoint){

        Auction findAuction = auctionRepository.findByIdOrElseThrow(auctionId);
        int totalPoint = lastTotalPoint(loginUserId)-usePoint;
        if (lastTotalPoint(loginUserId) < usePoint){
            throw new CustomException(ErrorCode.POINT_NOT_ENOUGH);
        }
        Point point = new Point(loginUserId
                ,findAuction
                ,PointReason.AUCTION_BID
                ,-usePoint
                ,totalPoint);
        pointRepository.save(point);

        return PointResponseDto.toDto(point);
    }

    public PointEarnResponseDto earnPoint (Long userId , int earnPoint){

        int totalPoint = earnPoint + lastTotalPoint(userId);
        Point point = new Point(userId,PointReason.EARN,earnPoint,totalPoint);
        pointRepository.save(point);
        return PointEarnResponseDto.toDto(point);
    }

    private int lastTotalPoint(Long userId){

        Optional<Integer> lastTotalPoint = pointRepository.findByLastTotalPoint(userId);
        if (lastTotalPoint.isPresent()){
            int p = lastTotalPoint.get();
            return p;
        }
        return 0;
    }
}
