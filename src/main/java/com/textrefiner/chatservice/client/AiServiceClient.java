package com.textrefiner.chatservice.client;

import com.textrefiner.chatservice.dto.ai.AiRefineRequest;
import com.textrefiner.chatservice.dto.ai.AiRefineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// AI 서비스
@FeignClient(name = "ai-service")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/refine")
    AiRefineResponse refineText(@RequestBody AiRefineRequest request);
}