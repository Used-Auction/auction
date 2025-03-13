package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Auction.Dto.AuctionResponseDto;
import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;


    /**
     * <p>경매 등록</p>
     * @param loginUserId 로그인유저 식별자
     * @param requestDto {@link AuctionRequestDto}
     * @return AuctionResponseDto {@link AuctionResponseDto}
     */
    public AuctionResponseDto aadAuction (Long loginUserId , AuctionRequestDto requestDto){

        validExpiredAt(requestDto.getExpiredAt());
        LocalDateTime expiredAt = getExpiredAtFromLocalDate(requestDto.getExpiredAt());
        Auction auction = new Auction(loginUserId , requestDto.getProductId() , requestDto , expiredAt);
        auctionRepository.save(auction);
        return AuctionResponseDto.toDto(auction);
    }

    /**
     * @param expiredAt 경매종료시간은 등록일 기준 3일 후부터 가능
     */
    private static void validExpiredAt(LocalDate expiredAt) {
        LocalDate now = LocalDate.now();
        LocalDate minExpired = now.plusDays(3);
        if (expiredAt.isBefore(minExpired)){
            throw new CustomException(ErrorCode.EXPIRED_ERROR);
        }
    }

    /**
     * 매일 23:59:59 scheduler 동작을 위한 LocalDate -> LocalDateTime 변환
     * @param expiredAt 경매 만료기한
     * @return LocalDateTime
     */
    public LocalDateTime getExpiredAtFromLocalDate(LocalDate expiredAt){
        String expiredAtSt = expiredAt.toString();
        return LocalDateTime.parse(expiredAtSt+" 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Transactional
    public void expiredAuction(){
        auctionRepository.expiredAuction(AuctionStatus.EXPIRED);
    }
  
}
