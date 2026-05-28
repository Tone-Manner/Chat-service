package com.textrefiner.chatservice.exception;

import com.textrefiner.chatservice.dto.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 다른 마이크로서비스(유저 서비스)와 통신하다가 에러가 났을 경우
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        // 상대방 서비스가 보낸 상태 코드(예: 400, 403)를 그대로 가져옴
        int status = e.status() != -1 ? e.status() : 500;

        // 에러 메시지 가공 (실무에서는 e.contentUTF8() 로 원본 JSON을 파싱하기도 함)
        String errorMessage = "유저 서비스 통신 중 에러가 발생했습니다. (권한 부족 또는 토큰 만료)";

        ErrorResponse response = new ErrorResponse(status, errorMessage);
        return ResponseEntity.status(status).body(response);
    }

    // 2. 그 외 채팅 서비스 내부에서 터지는 알 수 없는 에러들
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류가 발생했습니다: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}