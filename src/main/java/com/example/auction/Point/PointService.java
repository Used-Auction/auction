package com.example.auction.Point;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Point.Dto.PointEarnResponseDto;
import com.example.auction.Point.Dto.PointResponseDto;
import com.example.auction.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final AuctionRepository auctionRepository;

    /**
     * <p>경매 입찰 포인트 계산</p>
     * @param loginUserId  로그인유저식별자
     * @param auctionId  경매 식별자
     * @param usePoint  사용된 point
     */
    public void bidPoint (Long loginUserId , Long auctionId , int usePoint){

        int totalPoint = lastTotalPoint(loginUserId)-usePoint;
        if (lastTotalPoint(loginUserId) < usePoint){
            throw new CustomException(ErrorCode.POINT_NOT_ENOUGH);
        }
        Point point = new Point(loginUserId
                ,auctionId
                ,PointReason.AUCTION_BID
                ,-usePoint
                ,totalPoint);
        pointRepository.save(point);
    }

    /**
     * <p>포인트 적립</p>
     * @param loginUserId 로그인유저식별자
     * @param earnPoint 적립되는 포인트
     * @return PointEarnResponseDto {@link PointEarnResponseDto}
     */
    public PointEarnResponseDto earnPoint (Long loginUserId , int earnPoint){

        int totalPoint = earnPoint + lastTotalPoint(loginUserId);
        Point point = new Point(loginUserId,PointReason.EARN,earnPoint,totalPoint);
        pointRepository.save(point);
        return PointEarnResponseDto.toDto(point);
    }


    /**
     * <p>해당 사용자의 마지막 total_point 추출</p>
     * @param userId 사용자 식별자
     * @return int 해당 사용자의 마지막 total_point
     */
    public int lastTotalPoint(Long userId){

        Optional<Integer> lastTotalPoint = pointRepository.findByLastTotalPoint(userId);
        if (lastTotalPoint.isPresent()){
            int p = lastTotalPoint.get();
            return p;
        }
        return 0;
    }
}
