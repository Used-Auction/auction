package com.example.auction.AuctionRecord;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.Auction.AuctionService;
import com.example.auction.AuctionRecord.Dto.AuctionRecordResponseDto;
import com.example.auction.Point.Point;
import com.example.auction.Point.PointReason;
import com.example.auction.Point.PointRepository;
import com.example.auction.Point.PointService;
import com.example.auction.User.entity.Role;
import com.example.auction.User.entity.User;
import com.example.auction.User.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuctionRecordServiceTest {

    @Autowired
    private AuctionRecordService auctionRecordService;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private PointService pointService;

    @BeforeEach
    void setUp() {

        Auction auction = new Auction(1L,1L,1000, LocalDateTime.parse("2025-03-17T23:59:59"));
                auctionRepository.save(auction);
        Auction findAuction = auctionRepository.findByIdOrElseThrow(1L);
        System.out.println(""+findAuction.getId());

//        for (int i = 0; i<1000000 ;i++ ){
//            Long userId = 1L;
//            int earnPoint = 1000;
//            int totalPoint = pointService.lastTotalPoint(userId)+earnPoint;
//            Point point = new Point(userId, PointReason.EARN,earnPoint,totalPoint);
//            pointRepository.save(point);
//        }
        System.out.println("total point : "+ pointService.lastTotalPoint(1L));
    }

    @AfterEach
    void tearDown() {

    }



    @Test
    void bidAuctionUsingLock() {
        IntStream.range(1, 10).parallel().forEach(i -> auctionRecordService.bidAuctionUsingLock((long) i,1L,1000));
        auctionRecordService.getBidCount(1L);
    }

    @Test
    @DisplayName("data 많을 경우 테스트")
    void getTotalPoint(){
        pointService.bidPoint(1L,1L,5000);
        System.out.println("total point :"+ pointService.lastTotalPoint(1L));

    }
}