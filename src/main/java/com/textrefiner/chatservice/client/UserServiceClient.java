package com.textrefiner.chatservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

// name 에는 Eureka에 등록된 유저 서비스의 이름과 동일하게 작성
@FeignClient(name = "user-service")
public interface UserServiceClient {

    // 유저 서비스에서 만들었던 '대화창 사용 권한 요청' API 주소
    @PostMapping("/api/v1/users/chat-rooms/use")
    ResponseEntity<String> useChatRoom(@RequestHeader("Authorization") String token);

}