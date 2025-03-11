package com.example.auction.Auction.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuctionRequestDto {

    private Long productId;

    private int minPoint;

    private LocalDateTime expiredAt;

}
