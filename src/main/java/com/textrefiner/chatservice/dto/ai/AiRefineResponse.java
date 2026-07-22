package com.textrefiner.chatservice.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiRefineResponse {
    private List<String> refinedTexts;
}