package com.textrefiner.chatservice.service;

import com.textrefiner.chatservice.dto.MessageRequest;
import com.textrefiner.chatservice.entity.ChatMessage;
import com.textrefiner.chatservice.entity.MessageRole;
import com.textrefiner.chatservice.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    // 1. 메시지 전송 및 임시 AI 응답 저장
    @Transactional
    public ChatMessage sendMessage(Long roomId, MessageRequest request) {
        // 1. 유저가 보낸 메시지 DB에 저장
        ChatMessage userMessage = new ChatMessage(roomId, MessageRole.USER, request.getContent());
        chatMessageRepository.save(userMessage);

        // 2. [임시] 진짜 AI 서비스가 없으므로 가짜 응답을 하나 만들어서 바로 저장
        // (나중에 AI 서비스가 완성되면, 이 부분에서 AI 서비스로 Feign 통신을 걸어서 결과를 받아올 예정)
        String dummyAiResponse = "AI가 다듬은 문장: [" + request.getContent() + "] -> (아직 AI 서비스 연결 전입니다.)";
        ChatMessage aiMessage = new ChatMessage(roomId, MessageRole.AI, dummyAiResponse);
        chatMessageRepository.save(aiMessage);

        // 프론트엔드에게는 유저가 화면에 바로 띄울 수 있게 유저의 메시지(혹은 AI의 메시지)를 반환
        return userMessage;
    }

    // 2. 특정 방의 전체 대화 내역 불러오기
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessages(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }
}