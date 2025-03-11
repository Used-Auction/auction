package com.example.auction.User.service;


import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.User.dto.UserResponseDto;
import com.example.auction.User.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p> ADMIN 권한 관련 서비스. </p>
 *
 * @author Seokgyu Hwang (Chris)
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "Security::AdminService")
public class AdminService {

  // ADMIN 권한으로 처리할 로직을 정의합니다.

  public UserResponseDto doSomethingAsAdmin() {
    // 인증 객체를 이용해 로그인 한 사용자의 정보를 가져온다.
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userDetails.getUser();

    log.info("ADMIN 로직 실행.");

    return new UserResponseDto(
        user.getId(),
        user.getEmail(),
        user.getRole().getName()
    );
  }
}
