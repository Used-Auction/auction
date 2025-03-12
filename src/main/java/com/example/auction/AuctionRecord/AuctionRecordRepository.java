package com.example.auction.AuctionRecord;

import com.example.auction.Auction.Auction;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionRecordRepository extends JpaRepository<AuctionRecord , Long> {

   default AuctionRecord findByIdOrElseThrow(Long id){
       return findById(id).orElseThrow(()->new CustomException(ErrorCode.RECORD_NOT_FOUND));
   }

    Optional<AuctionRecord> findByAuctionId(Long auctionId);

}
