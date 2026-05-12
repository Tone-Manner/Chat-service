package com.textrefiner.chatservice.service;

import com.textrefiner.chatservice.client.UserServiceClient;
import com.textrefiner.chatservice.entity.ChatRoom;
import com.textrefiner.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserServiceClient userServiceClient; // Day 2에 만든 전화기!

    // 1. 대화창 생성 로직 (유저 서비스 통신 포함)
    @Transactional
    public ChatRoom createRoom(String token, String email) {

        // 유저 서비스에 방 만들어도 되는지 물어보기
        // 만약 4개 다 썼다면 여기서 FeignException(403)이 발생 하면서 아래 방 생성 로직은 실행 안 됨.
        userServiceClient.useChatRoom(token);

        // 통과 시 방 생성
        ChatRoom room = new ChatRoom(email, "새로운 대화창");
        return chatRoomRepository.save(room);
    }

    // 2. 내 대화창 목록 조회 로직
    @Transactional(readOnly = true)
    public List<ChatRoom> getMyRooms(String email) {
        return chatRoomRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }
}