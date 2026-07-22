package com.textrefiner.chatservice.dto.ai;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class AiRefineRequest {
    private String text;
    private String relation;
}