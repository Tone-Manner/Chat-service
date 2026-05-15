package com.textrefiner.chatservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId; // 이 메시지가 어느 방에 속해있는지 (ChatRoom의 ID)

    @Enumerated(EnumType.STRING)
    private MessageRole role; // USER 인지 AI 인지

    @Column(columnDefinition = "TEXT") // 문자가 길어질 수 있으니 TEXT 타입으로!
    private String content; // 내용

    private LocalDateTime createdAt;

    public ChatMessage(Long roomId, MessageRole role, String content) {
        this.roomId = roomId;
        this.role = role;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}