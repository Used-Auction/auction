package com.example.auction.User.entity;

import com.example.auction.Global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private Long point;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String status;

    public User (String email, String password, Role role, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
