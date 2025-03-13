package com.example.auction.Auction;

import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    default Auction findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CustomException(ErrorCode.AUCTION_NOT_FOUND));
    }

    @Modifying
    @Query(value = "update Auction a set a.status = :status where a.expiredAt < now()")
    void expiredAuction(@Param("status") AuctionStatus status);

}
