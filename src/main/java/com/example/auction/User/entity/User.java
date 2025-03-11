package com.example.auction.User.entity;

import com.example.auction.Global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private Long point;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String status;

    public User() {
    }

    public User(String email, String password, String name, String phoneNumber, Long point, Role role, String status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.role = role;
        this.status = status;
    }
}
