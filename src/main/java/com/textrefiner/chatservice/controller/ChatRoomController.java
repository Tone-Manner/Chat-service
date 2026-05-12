package com.textrefiner.chatservice.controller;

import com.textrefiner.chatservice.entity.ChatRoom;
import com.textrefiner.chatservice.service.ChatRoomService;
import com.textrefiner.chatservice.util.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chats/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtParser jwtParser;

    // 1. 방 생성 API
    @PostMapping
    public ResponseEntity<ChatRoom> createRoom(@RequestHeader("Authorization") String token) {
        String email = jwtParser.extractEmail(token);
        ChatRoom room = chatRoomService.createRoom(token, email);
        return ResponseEntity.ok(room);
    }

    // 2. 내 방 목록 조회 API
    @GetMapping
    public ResponseEntity<List<ChatRoom>> getMyRooms(@RequestHeader("Authorization") String token) {
        String email = jwtParser.extractEmail(token);
        List<ChatRoom> rooms = chatRoomService.getMyRooms(email);
        return ResponseEntity.ok(rooms);
    }
}