package com.example.auction.AuctionRecord;

import com.example.auction.Auction.Auction;
import com.example.auction.Auction.AuctionRepository;
import com.example.auction.Auction.AuctionService;
import com.example.auction.User.entity.Role;
import com.example.auction.User.entity.User;
import com.example.auction.User.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        Auction auction = new Auction(1L,1L,1000, LocalDateTime.parse("2025-03-17T23:59:59"));
                auctionRepository.save(auction);
        Auction findAuction = auctionRepository.findByIdOrElseThrow(1L);
        System.out.println(""+findAuction.getId());

        for (int i = 0; i<100 ;i++ ){
            Long userId = (long)i;
            String userEmail = "test"+i+"@naver.com";
            String password = "aaa111!";
            String name = "test"+i;
            String number = "010-1234-5953";
            User user = new User(userEmail,password, Role.USER,name,number);
            userRepository.save(user);
        }
        int userCount = (int) userRepository.count();
        System.out.println("user count : "+userCount);



    }

    @AfterEach
    void tearDown() {
        auctionRecordService.reset();
        userRepository.deleteAll();
    }

    @Test
    void bidAuctionUsingLock() {
        IntStream.range(1, 10).parallel().forEach(i -> auctionRecordService.bidAuctionUsingLock((long) i,1L,1000));
        auctionRecordService.getBidCount(1L);
    }
}