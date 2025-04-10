package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username); // ユーザー名でユーザーを取得
}
