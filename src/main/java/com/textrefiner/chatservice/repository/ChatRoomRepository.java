package com.textrefiner.chatservice.repository;

import com.textrefiner.chatservice.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 내 이메일로 만들어진 방들을 최신순으로
    List<ChatRoom> findByUserEmailOrderByCreatedAtDesc(String userEmail);
}