package com.example.auction.Auction;

import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    default Auction findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CustomException(ErrorCode.AUCTION_NOT_FOUND));
    }

}
