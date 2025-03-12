package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Auction.Dto.AuctionResponseDto;
import com.example.auction.AuctionRecord.AuctionRecordService;
import com.example.auction.AuctionRecord.Dto.AuctionRecordRequestDto;
import com.example.auction.AuctionRecord.Dto.AuctionRecordResponseDto;
import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Global.CommonResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionRecordService auctionRecordService;


    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<AuctionResponseDto>> addAuction(
            @RequestBody AuctionRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("경매 등록",
                auctionService.aadAuction(userDetails.getUser().getId(),requestDto)));
    }


    @PostMapping("/bid")
    public ResponseEntity<CommonResponseBody<AuctionRecordResponseDto>> bidAuction(
            @RequestBody AuctionRecordRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("상위 입찰",
                auctionRecordService.bidAuction(userDetails.getUser().getId(), requestDto.getAuctionId(), requestDto.getBidPoint())));
    }
}
