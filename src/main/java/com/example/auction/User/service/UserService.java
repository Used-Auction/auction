package com.example.auction.User.service;

import com.example.auction.Auth.UserDetailsImpl;
import com.example.auction.User.dto.UserResponseDto;
import com.example.auction.User.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Security::UserService")
public class UserService {

    public UserResponseDto doSomethingAsUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        log.info("USER 로직 실행.");

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}
