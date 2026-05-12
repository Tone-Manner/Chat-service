package com.textrefiner.chatservice.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class JwtParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 시크릿 키 검증 없이, Payload만 까서 이메일(sub)을 읽어오는 메서드
    public String extractEmail(String token) {
        try {
            String payload = token.replace("Bearer ", "").split("\\.")[1];
            String decoded = new String(Base64.getUrlDecoder().decode(payload));
            JsonNode jsonNode = objectMapper.readTree(decoded);
            return jsonNode.get("sub").asText();
        } catch (Exception e) {
            throw new RuntimeException("토큰 파싱 중 오류가 발생했습니다.");
        }
    }
}