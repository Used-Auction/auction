package com.example.auction.User.controller;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.User.dto.UserResponseDto;
import com.example.auction.User.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> ADMIN 권한으로 접근할 수 있는 API. </p>
 *
 * @author Seokgyu Hwang (Chris)
 * @version 1.0
 * @since 1.0
 */
@Slf4j(topic = "Security::AdminController")
@RestController
@RequestMapping(value = "/admins")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  /**
   * USER 권한으로 접근 테스트.
   *
   * @return {@code ResponseEntity<CommonResponseBody<MemberResponse>>}
   */
  @GetMapping("/something")
  public ResponseEntity<CommonResponseBody<UserResponseDto>> doSomethingAsAdmin() {
    UserResponseDto dto = this.adminService.doSomethingAsAdmin();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new CommonResponseBody<>("ADMIN 권한으로 접근 성공.", dto));
  }
}
