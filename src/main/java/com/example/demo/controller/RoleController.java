package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.domain.Users;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoleController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public RoleController(SimpMessagingTemplate messagingTemplate, UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    // 役割を取得するAPIエンドポイント
    @GetMapping("/role-info/{username}")
    public String getRole(@PathVariable String username) {
        Users user = userRepository.findByUsername(username); // ユーザー名で取得
        if (user == null) {
            return "ユーザーが見つかりません";
        }
        return user.getRole(); // ユーザーの権限を返す
    }

    @PostMapping("/change-role")
    public ResponseEntity<Void> changeRole(@RequestParam String username, @RequestParam String newRole) {
        Users user = userRepository.findByUsername(username);
        
        if (user == null) {
            return ResponseEntity.badRequest().build(); // ユーザーが見つからない場合
        }

        // ユーザーの権限を即座に更新
        user.setRole(newRole);
        userRepository.save(user); // データベースに保存

        // WebSocketで通知送信
        messagingTemplate.convertAndSend("/topic/role/" + username, newRole);
        return ResponseEntity.ok().build();
    }
}
