package com.example.auction.User.controller;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.User.dto.AccountRequestDto;
import com.example.auction.User.dto.JoinRequestDto;
import com.example.auction.User.dto.JwtAuthResponseDto;
import com.example.auction.User.dto.UserResponseDto;
import com.example.auction.User.service.AccountService;
import com.example.auction.User.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.dao.DuplicateKeyException;

@Slf4j(topic = "Security::UserController")
@Controller
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequestDto joinRequest, Model model) {
        try {
            accountService.createAccount(joinRequest, "USER");
            return "redirect:/users/login";  // 회원가입 후 로그인 페이지로 리다이렉트
        } catch (DuplicateKeyException e) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "join";  // 회원가입 페이지로 다시 이동
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "join";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // `login.html` 렌더링
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AccountRequestDto accountRequestDto, Model model) {
        try {
            JwtAuthResponseDto authResponse = this.accountService.login(accountRequestDto);
            model.addAttribute("token", authResponse.getToken()); // JWT 토큰 저장
            return "redirect:/dashboard"; // 로그인 성공 후 대시보드로 이동
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "이메일을 찾을 수 없습니다.");
            return "login"; // 로그인 페이지로 다시 이동
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }
}
