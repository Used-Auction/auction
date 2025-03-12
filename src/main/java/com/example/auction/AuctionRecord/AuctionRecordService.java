package com.example.auction.AuctionRecord;


import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.AuctionRecord.Dto.AuctionRecordResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionRecordService {

    private final AuctionRecordRepository auctionRecordRepository;
    private final AuctionRepository auctionRepository;
    private final PointService pointService;



    @Transactional
    public AuctionRecordResponseDto bidAuction(Long userId , Long auctionId , int bidPoint ){

        Optional<AuctionRecord> optionalAuctionRecord = auctionRecordRepository.findByAuctionId(auctionId);
        Auction findAuction = auctionRepository.findByIdOrElseThrow(auctionId);

        // 첫 상위 입찰시
        if (optionalAuctionRecord.isEmpty()){
            if (bidPoint < findAuction.getMinPoint()){
                throw new CustomException(ErrorCode.BID_NOT_ENOUGH);
            }
            pointService.bidPoint(userId,auctionId,bidPoint);
            AuctionRecord auctionRecord = new AuctionRecord(userId , auctionId , bidPoint);
            auctionRecordRepository.save(auctionRecord);
            return AuctionRecordResponseDto.toDto(auctionRecord);
        }
        // 이후 상위입찰시
        if (bidPoint < optionalAuctionRecord.get().getBidPoint()+1000){
            throw new CustomException(ErrorCode.BID_NOT_ENOUGH);
        }
        pointService.bidPoint(userId,auctionId,bidPoint);
        AuctionRecord auctionRecord = optionalAuctionRecord.get();
        auctionRecord.setTopBid(userId,bidPoint);
        auctionRecordRepository.save(auctionRecord);
        return AuctionRecordResponseDto.toDto(auctionRecord);
    }

}
