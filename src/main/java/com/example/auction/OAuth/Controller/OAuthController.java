package com.example.auction.OAuth.Controller;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.OAuth.Dto.KakaoLoginDto;
import com.example.auction.OAuth.Dto.KakaoTokenDto;
import com.example.auction.OAuth.Service.OAuthService;
import com.example.auction.User.dto.JwtAuthResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;

@Slf4j
@RestController
@AllArgsConstructor
public class OAuthController {

    private OAuthService oAuthService;

    @ResponseBody
    @GetMapping("/auth/kakao/LoginHandler")
    public ResponseEntity<CommonResponseBody<?>> kakaoCallback(@RequestParam String code) {

        KakaoTokenDto getToken = oAuthService.getKakaoAccessToken(code);

        if (getToken == null || getToken.getAccess_token() == null) {
            log.error("카카오 Access Token 발급 실패: {}", getToken);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponseBody<>("카카오 토큰 발급 실패", null));
        }

        String accessToken = getToken.getAccess_token();
        HashMap<String, Object> userInfo = oAuthService.getKakaoUserInfo(accessToken);
        String nickname = userInfo.get("nickname").toString();
        Long id = Long.parseLong(userInfo.get("id").toString());
        KakaoLoginDto loginDto = new KakaoLoginDto(nickname, id);
        JwtAuthResponseDto dto = oAuthService.login(loginDto);

        return ResponseEntity.ok(new CommonResponseBody<>("login success", dto));
    }

}