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

    public User(String email, String password, String name, String phoneNumber, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
