package com.textrefiner.chatservice.controller;

import com.textrefiner.chatservice.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatTestController {

    private final UserServiceClient userServiceClient;

    // 프론트엔드 -> 채팅 서비스, 채팅 서비스 -> 유저 서비스 릴레이 구조
    @PostMapping("/test-feign")
    public ResponseEntity<String> testFeign(@RequestHeader("Authorization") String token) {

        // 프론트엔드한테 받은 JWT 토큰을 그대로 쥐여주고 유저 서비스에 전달
        ResponseEntity<String> response = userServiceClient.useChatRoom(token);

        return ResponseEntity.ok("유저 서비스 응답 성공! 내용: " + response.getBody());
    }
}