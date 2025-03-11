package com.example.auction.User.repository;

import com.example.auction.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default User findUserById(Long id) {
        return this.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id의 유저가 존재하지 않습니다."));
    }
}
