package com.example.auction.User.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String email;

    private final String role;

    public UserResponseDto(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
