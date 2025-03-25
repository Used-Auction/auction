package com.example.auction.AuctionRecord;


import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.AuctionRecord.Dto.AuctionRecordResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Global.util.RedisBidPointRepository;
import com.example.auction.Point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionRecordService {

    private final AuctionRecordRepository auctionRecordRepository;
    private final AuctionRepository auctionRepository;
    private final PointService pointService;
    private final RedissonClient redissonClient;
    private static final String LOCK_KEY = "bidLock";

    @Transactional
    public AuctionRecordResponseDto bidAuction(Long userId , Long auctionId , int bidPoint ){
        RLock lock = redissonClient.getFairLock(LOCK_KEY);
        try {
            boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
            if (isLocked) {
                Optional<AuctionRecord> optionalAuctionRecord = auctionRecordRepository.findByAuctionId(auctionId);
                Auction findAuction = auctionRepository.findByIdOrElseThrow(auctionId);
                AuctionRecord auctionRecord;
                if (bidPoint < findAuction.getMinPoint()){
                    throw new CustomException(ErrorCode.BID_NOT_ENOUGH);
                }
                // 첫 상위 입찰시
                if (optionalAuctionRecord.isEmpty()){
                    pointService.bidPoint(userId,auctionId,bidPoint);
                    auctionRecord = new AuctionRecord(userId , auctionId , bidPoint);
                    auctionRecord.incrementBidCount();
                    auctionRecordRepository.save(auctionRecord);
                    return AuctionRecordResponseDto.toDto(auctionRecord);
                }
                // 이후 상위입찰시
                auctionRecord = optionalAuctionRecord.get();
                if (bidPoint < auctionRecord.getBidPoint()+1000){
                    throw new CustomException(ErrorCode.BID_NOT_ENOUGH);
                }
                pointService.bidPoint(userId,auctionId,bidPoint);
                auctionRecord.setTopBid(userId,bidPoint);
                auctionRecord.incrementBidCount();
                auctionRecordRepository.save(auctionRecord);
                return AuctionRecordResponseDto.toDto(auctionRecord);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

    @Transactional
    public void bidAuctionUsingLock(Long userId , Long auctionId , int bidPoint) {
        RLock lock = redissonClient.getFairLock(LOCK_KEY);
        try {
            boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
            if (isLocked) {
                Optional<AuctionRecord> optionalAuctionRecord = auctionRecordRepository.findByAuctionId(auctionId);
                AuctionRecord auctionRecord;
                if (optionalAuctionRecord.isEmpty()){
                    auctionRecord = new AuctionRecord(userId, auctionId, bidPoint);
                    auctionRecord.incrementBidCount();
                    auctionRecordRepository.save(auctionRecord);
                }else {
                    auctionRecord = optionalAuctionRecord.get();
                    if (bidPoint > auctionRecord.getBidPoint()){
                        auctionRecord.incrementBidCount();
                        auctionRecord.setTopBid(userId,bidPoint);
                        auctionRecordRepository.save(auctionRecord);
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public void reset(){
        AuctionRecord auctionRecord = auctionRecordRepository.findByIdOrElseThrow(1L);
        auctionRecord.initBidPoint();
        auctionRecordRepository.save(auctionRecord);
    }

    public void getBidCount(Long auctionRecordId){
        AuctionRecord auctionRecord = auctionRecordRepository.findByIdOrElseThrow(1L);
        log.info("bidCount : {}" , auctionRecord.getBidCount());
    }

    private String setAuctionData(Long userId , int bidPoint){
        return userId+":"+bidPoint;
    }

    public void modifyTopBid(Long auctionRecordId,Long userId,int bidPoint){
        AuctionRecord auctionRecord = auctionRecordRepository.findByIdOrElseThrow(auctionRecordId);
        Long previousBidder = auctionRecord.getUserId();

    }
}
