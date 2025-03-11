package com.example.auction.User;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {

    USER("user"),

    ADMIN("admin");

    private final String name;

    Role(String name) {
        this.name = name;
    }
    public static Role of(String roleName) throws IllegalArgumentException {
        for (Role role : values()) {
            if (role.getName().equals(roleName.toLowerCase())) {
                return role;
            }
        }
        throw new IllegalArgumentException("해당하는 이름의 권한을 찾을 수 없습니다: " + roleName);
    }

//    public List<SimpleGrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
//    }
}
