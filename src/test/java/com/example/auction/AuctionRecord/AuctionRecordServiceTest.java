package com.example.auction.AuctionRecord;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.Auction.AuctionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        Auction auction = new Auction(1L,1L,1000, LocalDateTime.parse("2025-03-17T23:59:59"));
                auctionRepository.save(auction);
        Auction findAuction = auctionRepository.findByIdOrElseThrow(1L);
        System.out.println(""+findAuction.getId());
    }

    @AfterEach
    void tearDown() {
        auctionRecordService.reset();
    }

    @Test
    void bidAuctionUsingLock() {
        IntStream.range(0, 1000).parallel().forEach(i -> auctionRecordService.bidAuctionUsingLock((long) i,1L,i*1000));
        auctionRecordService.getBidCount(1L);
    }
}