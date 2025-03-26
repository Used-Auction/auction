package com.example.auction.Point;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Point.Dto.PointEarnResponseDto;
import com.example.auction.Point.Dto.PointResponseDto;
import com.example.auction.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

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
     * <p>경매입찰 실패로인한 포인트 환불</>
     * @param userId 유저 식별자
     * @param auctionId 경매 식별자
     * @param refundPoint 환불포인트
     */
    public void refundPoint (Long userId , Long auctionId , int refundPoint ){
        Point point = addPoint(PointReason.BID_REFUND,userId,refundPoint);
        point.setAuctionId(auctionId);
        pointRepository.save(point);
    }

    /**
     * <p>포인트 적립</p>
     * @param loginUserId 로그인유저식별자
     * @param earnPoint 적립되는 포인트
     * @return PointEarnResponseDto {@link PointEarnResponseDto}
     */
    public PointEarnResponseDto earnPoint (Long loginUserId , int earnPoint){
        Point point = addPoint(PointReason.EARN , loginUserId , earnPoint);
        pointRepository.save(point);
        return PointEarnResponseDto.toDto(point);
    }

    /**
     * <p>포인트 data 추가</p>
     * @param pointReason 포인트 사유
     * @param userId 해당유저 식별자
     * @param addPoint 포인트
     * @return Point entity {@link Point}
     */
    public Point addPoint(PointReason pointReason , Long userId , int addPoint){
        int totalPoint = addPoint + lastTotalPoint(userId);
        return new Point(userId,pointReason,addPoint,totalPoint);
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

    /**
     * <p>유저 포인트내역 리스트 조회</p>
     * @param userId 유저식별자
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @return Page<PointResponseDto>
     */
    public Page<PointResponseDto> getPointList(Long userId , int page , int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Point> pointPage = pointRepository.findByUserId(userId,pageable);
        return pointPage.map(PointResponseDto::toDto);
    }
}
