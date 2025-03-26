package com.example.auction.Auction;

import com.example.auction.Auction.Dto.AuctionRequestDto;
import com.example.auction.Auction.Dto.AuctionResponseDto;
import com.example.auction.AuctionRecord.AuctionRecordService;
import com.example.auction.AuctionRecord.Dto.AuctionRecordRequestDto;
import com.example.auction.AuctionRecord.Dto.AuctionRecordResponseDto;
import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.Global.CommonResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionRecordService auctionRecordService;

    /**
     * <p>경매 등록</p>
     * @param requestDto {@link AuctionRequestDto}
     * @param userDetails {@link UserDetailsImpl}
     * @return AuctionResponseDto {@link AuctionResponseDto}
     */
    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<AuctionResponseDto>> addAuction(
            @RequestBody AuctionRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("경매 등록",
                auctionService.aadAuction(userDetails.getUser().getId(),requestDto)));
    }

    /**
     * <p>경매 입찰</p>
     * @param requestDto {@link AuctionRecordRequestDto}
     * @param userDetails {@link UserDetailsImpl}
     * @return  AuctionRecordResponseDto {@link AuctionRecordResponseDto}
     */
    @PostMapping("/bid")
    public ResponseEntity<CommonResponseBody<AuctionRecordResponseDto>> bidAuction(
            @RequestBody AuctionRecordRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return ResponseEntity.ok().body(new CommonResponseBody<>("상위 입찰",
                auctionRecordService.bidAuction(userDetails.getUser().getId(), requestDto.getAuctionId(), requestDto.getBidPoint())));
    }

    /**
     *<p>경매 리스트 조회</p>
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @return Page<AuctionResponseDto>
     */
    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<Page<AuctionResponseDto>>> getAuctionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok().body(new CommonResponseBody<>("경매 리스트 조회",
                auctionService.getAuctionList(page,size)));
    }

    /**
     * <p>해당 경매 입찰내역 리스트 조회</p>
     * @param auctionId 경매 식별자
     * @param page 조회할 페이지 번호 (미입력시 defaultValue = "0")
     * @param size 조회할 페이지 크기 (미입력시 defaultValue = "10")
     * @return Page<AuctionRecordResponseDto>
     */
    @GetMapping("/{auctionId}/history")
    public ResponseEntity<CommonResponseBody<Page<AuctionRecordResponseDto>>> getAuctionHistory(
            @PathVariable Long auctionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok().body(new CommonResponseBody<>("해당 경매 입찰내역 리스트 조회",
                auctionRecordService.getAuctionHistory(auctionId,page,size)));
    }

    /**
     * <p>경매 단건 조회</p>
     * @param auctionId 조회할 경매식별자
     * @return AuctionResponseDto {@link AuctionResponseDto}
     */
    @GetMapping("/{auctionId}")
    public ResponseEntity<CommonResponseBody<AuctionResponseDto>> getAuction(
            @PathVariable Long auctionId){
        return ResponseEntity.ok().body(new CommonResponseBody<>("경매 단건 조회",
                auctionService.getAuction(auctionId)));
    }
}
