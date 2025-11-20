package com.example.project_backend04.service.IService;

import com.example.project_backend04.dto.request.Chat.ChatMessageDto;
import com.example.project_backend04.dto.request.Chat.ConversationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IChatService {
    // Create or get existing 1-1 room between two users
    Long getOrCreateOneToOneRoom(Long userAId, Long userBId);

    // Create group room
    Long createGroupRoom(String name, List<Long> memberIds);

    // Send message
    ChatMessageDto sendMessage(Long roomId, Long senderId, String message);


    // Sử dụng đúng Pageable của Spring
    Page<ChatMessageDto> getMessages(Long roomId, Pageable pageable);

    // Fetch conversations for a user (like messenger)
    List<ConversationDto> listConversations(Long userId);
}
