package com.textrefiner.chatservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail; // 이 방의 주인!
    private String title;     // 방 제목 (예: "새로운 대화창")
    private LocalDateTime createdAt;

    public ChatRoom(String userEmail, String title) {
        this.userEmail = userEmail;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }
}