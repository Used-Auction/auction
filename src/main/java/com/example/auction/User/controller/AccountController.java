package com.example.auction.User.controller;


import com.example.auction.Global.CommonResponseBody;
import com.example.auction.User.dto.AccountRequestDto;
import com.example.auction.User.dto.JoinRequestDto;
import com.example.auction.User.dto.JwtAuthResponseDto;
import com.example.auction.User.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 가입 및 로그인을 위한 API. </p>
 *
 * @author Seokgyu Hwang (Chris)
 * @version 1.0
 * @since 1.0
 */
@Slf4j(topic = "Security::AccountController")
@Controller
@RequestMapping(value = "/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  /**
   * 회원가입.
   */
//  @PostMapping("/join")
//  public ResponseEntity<CommonResponseBody<String>> join(
//      @Valid @RequestBody JoinRequestDto dto,
//      @NotBlank @RequestParam String role) {
//    this.accountService.createAccount(dto, role);
//
//    return ResponseEntity
//        .status(HttpStatus.CREATED)
//        .body(new CommonResponseBody<>("회원가입 완료."));
//  }
  @GetMapping("/join")
  public String joinPage() {
    return "join"; // `join.html`을 반환하여 회원가입 페이지 렌더링
  }

  @PostMapping("/join")
  public String join(@ModelAttribute JoinRequestDto joinRequest, Model model) {
    try {
      accountService.createAccount(joinRequest, "USER");
      return "redirect:/accounts/login";  // 회원가입 후 로그인 페이지로 리다이렉트
    } catch (DuplicateKeyException e) {
      model.addAttribute("error", "이미 사용 중인 이메일입니다.");
      return "join";  // 회원가입 페이지로 다시 이동
    } catch (Exception e) {
      model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
      return "join";
    }
  }

  /**
   * 로그인.
   */
  @GetMapping("/login")
  public String loginPage() {
    return "login"; // `login.html` 렌더링
  }
//  @PostMapping("/login")
//  public String login(@ModelAttribute AccountRequestDto accountRequestDto, Model model) {
//    try {
//      JwtAuthResponseDto authResponse = this.accountService.login(accountRequestDto);
//      model.addAttribute("token", authResponse.getToken()); // JWT 토큰 저장
//      return "redirect:/dashboard"; // 로그인 성공 후 대시보드로 이동
//    } catch (UsernameNotFoundException e) {
//      model.addAttribute("error", "이메일을 찾을 수 없습니다.");
//      return "login"; // 로그인 페이지로 다시 이동
//    } catch (IllegalArgumentException e) {
//      model.addAttribute("error", "비밀번호가 올바르지 않습니다.");
//      return "login";
//    }
  @PostMapping("/login")
  public ResponseEntity<CommonResponseBody<JwtAuthResponseDto>> login(
      @Valid @RequestBody AccountRequestDto accountRequestDto) {
    JwtAuthResponseDto authResponse = this.accountService.login(accountRequestDto);

    return ResponseEntity.ok(new CommonResponseBody<>("로그인 성공", authResponse));
  }

  /**
   * 로그아웃.
   */

  @PostMapping("/logout")
  public ResponseEntity<CommonResponseBody<String>> logout(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws UsernameNotFoundException {
    // 인증 정보가 있다면 로그아웃 처리.
    if (authentication != null && authentication.isAuthenticated()) {
      new SecurityContextLogoutHandler().logout(request, response, authentication);

      log.info("인증 객체의 삭제 확인: {}", SecurityContextHolder.getContext().getAuthentication() == null);
      return ResponseEntity.ok(new CommonResponseBody<>("로그아웃 성공."));
    }

    // 인증 정보가 없다면 인증되지 않았기 때문에 로그인 필요.
    throw new UsernameNotFoundException("로그인이 먼저 필요합니다.");
  }

  /**
   * 현재 사용자의 인증 정보를 확인하기 위한 엔드 포인트.
   *
   * @param authentication 인증 객체 (Authentication은 controller에서 주입받을 수 있음)
   * @return {@code ResponseEntity<CommonResponseBody<?>>}
   */
  @GetMapping("/me")
  public ResponseEntity<CommonResponseBody<?>> aboutMe(Authentication authentication) {
    return ResponseEntity.ok(
        new CommonResponseBody<>(
            "현재 사용자의 인증 객체에 대한 정보입니다.",
            authentication.getPrincipal()
        ));
  }
}
