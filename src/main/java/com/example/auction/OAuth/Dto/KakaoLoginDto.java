package com.example.auction.OAuth.Dto;

import lombok.Getter;

@Getter
public class KakaoLoginDto {

    private String nickname;

    private Long id;
    private String tokenAuthScheme;

    private String accessToken;


    public KakaoLoginDto(String nickname, Long id) {
        this.nickname = nickname;
        this.id = id;
    }
}
