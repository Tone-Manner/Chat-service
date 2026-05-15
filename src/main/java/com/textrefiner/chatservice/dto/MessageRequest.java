package com.textrefiner.chatservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageRequest {
    private String content; // 유저가 다듬어 달라고 보낼 원본 텍스트
}