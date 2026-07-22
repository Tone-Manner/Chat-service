package com.textrefiner.chatservice.service;

import com.textrefiner.chatservice.client.AiServiceClient;
import com.textrefiner.chatservice.dto.MessageRequest;
import com.textrefiner.chatservice.dto.ai.AiRefineRequest;
import com.textrefiner.chatservice.dto.ai.AiRefineResponse;
import com.textrefiner.chatservice.entity.ChatMessage;
import com.textrefiner.chatservice.entity.ChatRoom;
import com.textrefiner.chatservice.entity.MessageRole;
import com.textrefiner.chatservice.repository.ChatMessageRepository;
import com.textrefiner.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final AiServiceClient aiServiceClient;

    // 1. 메시지 전송 및 임시 AI 응답 저장
    @Transactional
    public ChatMessage sendMessage(Long roomId, MessageRequest request) {
        // 1. 유저가 보낸 날것의 메시지 DB에 저장
        ChatMessage userMessage = new ChatMessage(roomId, MessageRole.USER, request.getContent());
        chatMessageRepository.save(userMessage);

        // 2. 현재 대화 중인 방 정보를 가져와서 누구(relation)와 대화하는지 확인
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("방을 찾을 수 없습니다."));

        // 3. AI 서비스에게 보낼 데이터(주문서) 조립
        AiRefineRequest aiRequest = AiRefineRequest.builder()
                .text(request.getContent())   // 유저가 입력한 원본 문장
                .relation(room.getRelation()) // 이 방의 타겟 (예: "직장 상사")
                .build();

        // 4. AI 서비스에 전화 걸어서 다듬어진 문장 5개 받아오기
        AiRefineResponse aiResponse = aiServiceClient.refineText(aiRequest);

        // 5. 받아온 5개의 문장을 화면에 띄우기 위해 하나의 말풍선 텍스트로 합치기
        List<String> texts = aiResponse.getRefinedTexts();
        StringBuilder aiReply = new StringBuilder("💡 추천하는 5가지 표현입니다:\n\n");
        for (int i = 0; i < texts.size(); i++) {
            aiReply.append(i + 1).append(". ").append(texts.get(i)).append("\n");
        }

        // 6. AI가 추천해준 메시지를 MessageRole.AI 권한으로 DB에 저장
        ChatMessage aiMessage = new ChatMessage(roomId, MessageRole.AI, aiReply.toString().trim());
        chatMessageRepository.save(aiMessage);

        // 7. 프론트엔드에게는 유저 메시지 반환 (기존 리턴 방식 유지)
        return userMessage;
    }

    // 2. 특정 방의 전체 대화 내역 불러오기
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessages(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }
}