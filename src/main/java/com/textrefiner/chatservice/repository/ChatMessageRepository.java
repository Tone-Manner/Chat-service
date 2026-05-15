package com.textrefiner.chatservice.repository;

import com.textrefiner.chatservice.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 특정 방의 대화 내역을 시간순(과거->최신)으로 정렬해서 가져오기
    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(Long roomId);
}