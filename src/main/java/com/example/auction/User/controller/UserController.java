package com.example.auction.User.controller;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.User.dto.UserResponseDto;
import com.example.auction.User.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j(topic = "Security::UserController")
@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }
    /**
     * USER 권한으로 접근 테스트.
     *
     * @return {@code ResponseEntity<CommonResponseBody<MemberResponse>>}
     */
    @GetMapping("/something")
    public ResponseEntity<CommonResponseBody<UserResponseDto>> doSomethingAsUser() {
        UserResponseDto dto = this.userService.doSomethingAsUser();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonResponseBody<>("USER 권한으로 접근 성공.", dto));
    }
}
