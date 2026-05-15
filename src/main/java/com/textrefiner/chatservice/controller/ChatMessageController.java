package com.textrefiner.chatservice.controller;

import com.textrefiner.chatservice.dto.MessageRequest;
import com.textrefiner.chatservice.entity.ChatMessage;
import com.textrefiner.chatservice.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats/rooms/{roomId}/messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    // 1. 특정 방에 메시지 보내기
    @PostMapping
    public ResponseEntity<ChatMessage> sendMessage(
            @PathVariable("roomId") Long roomId,
            @RequestBody MessageRequest request) {

        ChatMessage savedMessage = chatMessageService.sendMessage(roomId, request);
        return ResponseEntity.ok(savedMessage);
    }

    // 2. 특정 방의 모든 대화 내역 불러오기
    @GetMapping
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable("roomId") Long roomId) {
        List<ChatMessage> messages = chatMessageService.getMessages(roomId);
        return ResponseEntity.ok(messages);
    }
}
