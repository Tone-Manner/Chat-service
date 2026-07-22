package com.textrefiner.chatservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomCreateRequest {
    private String relation; // 프론트에서 넘겨줄 대상 ("부모님", "직장 상사" 등)
}