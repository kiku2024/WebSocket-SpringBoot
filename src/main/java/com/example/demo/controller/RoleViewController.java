package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.UserRepository;
import com.example.domain.Users;

import org.springframework.ui.Model;

@Controller
public class RoleViewController {

    private final UserRepository userRepository;

    public RoleViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/role-view")
    public String getRoleView(Model model) {
        // 最初のユーザーをデータベースから取得（例: 最初のユーザー）
        Users user = userRepository.findAll().stream().findFirst().orElse(null);

        // ユーザーが存在しない場合はエラーメッセージを設定
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        } else {
            model.addAttribute("username", "ゲスト");
        }

        return "view"; // Thymeleafテンプレート名（view.html）
    }
}
