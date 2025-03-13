package com.example.auction.Auction.Dto;


import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AuctionRequestDto {

    private Long productId;

    private int minPoint;

    private LocalDate expiredAt;

}
