package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Auction.Dto.AuctionResponseDto;
import com.example.auction.Global.CommonResponseBody;
import com.example.auction.Product.Dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<AuctionResponseDto>> addAuction(
            @Valid @RequestBody AuctionRequestDto requestDto
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("경매 등록",
                auctionService.aadAuction(1L,requestDto)));
    }
}
