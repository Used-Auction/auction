package com.example.auction.AuctionRecord;


import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.AuctionRecord.Dto.AuctionRecordResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Global.util.RedisBidPointRepository;
import com.example.auction.Point.Point;
import com.example.auction.Point.PointReason;
import com.example.auction.Point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * <p>경매 입찰</p>
     * @param userId 유저 식별자
     * @param auctionId 경매 식별자
     * @param bidPoint 경매 입찰 포인트
     * @return AuctionRecordResponseDto {@link AuctionRecordResponseDto}
     */
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
                    auctionRecordRepository.save(auctionRecord);
                    return AuctionRecordResponseDto.toDto(auctionRecord);
                }
                // 이후 상위입찰시
                auctionRecord = optionalAuctionRecord.get();
                if (bidPoint < auctionRecord.getBidPoint()+1000){
                    throw new CustomException(ErrorCode.BID_NOT_ENOUGH);
                }
                pointService.refundPoint(auctionRecord.getUserId(),auctionRecord.getAuctionId(),auctionRecord.getBidPoint());
                pointService.bidPoint(userId,auctionId,bidPoint);
                AuctionRecord newAuctionRecord = new AuctionRecord(userId,auctionId,bidPoint);
                auctionRecordRepository.save(newAuctionRecord);
                return AuctionRecordResponseDto.toDto(newAuctionRecord);
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

    /**
     * <p>해당 경매내역 리스트 조회</p>
     * @param auctionId 경매 식별자
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @return Page<AuctionRecordResponseDto>
     */
    public Page<AuctionRecordResponseDto> getAuctionHistory(Long auctionId , int page , int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AuctionRecord> auctionRecordPage = auctionRecordRepository.findByAuctionId(auctionId,pageable);
        return auctionRecordPage.map(AuctionRecordResponseDto::toDto);
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

}
