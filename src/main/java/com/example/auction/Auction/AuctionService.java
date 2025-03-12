package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Auction.Dto.AuctionResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Product.Product;
import com.example.auction.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;



    public AuctionResponseDto aadAuction (Long loginUserId , AuctionRequestDto requestDto){

        validExpiredAt(requestDto.getExpiredAt());
        Auction auction = new Auction(loginUserId , requestDto.getProductId() , requestDto);
        auctionRepository.save(auction);
        return AuctionResponseDto.toDto(auction);
    }

    /**
     * @param expiredAt 경매종료시간은 등록일 기준 3일 후부터 가능
     */
    private static void validExpiredAt(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minExpired = now.plusDays(3);
        if (expiredAt.isBefore(minExpired)){
            throw new CustomException(ErrorCode.EXPIRED_ERROR);
        }
    }
  
}
