package com.example.project_backend04.controller;


import com.example.project_backend04.dto.request.Chat.ChatMessageDto;
import com.example.project_backend04.dto.request.Chat.ConversationDto;
import com.example.project_backend04.dto.request.Chat.CreateRoomRequest;
import com.example.project_backend04.dto.request.Chat.SendMessageRequest;
import com.example.project_backend04.service.IService.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private IChatService chatService;

    // Tạo room group (admin or creator)
    @PostMapping("/rooms")
    public Long createRoom(@RequestBody CreateRoomRequest req) {
        return chatService.createGroupRoom(req.getName(), req.getMemberIds());
    }

    // Get or create 1-1 room
    @PostMapping("/rooms/1to1")
    public Long getOrCreate1to1(@RequestParam Long userA, @RequestParam Long userB) {
        return chatService.getOrCreateOneToOneRoom(userA, userB);
    }

    // Send message via REST (useful for testing)
    @PostMapping("/messages")
    public ChatMessageDto sendMessage(@RequestBody SendMessageRequest req) {
        ChatMessageDto dto = chatService.sendMessage(req.getRoomId(), req.getSenderId(), req.getMessage());
        return dto;
    }

    // Get messages for a room (page, size) - newest first
    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessageDto> getMessages(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable p = PageRequest.of(page, size);
        return chatService.getMessages(roomId, p).getContent();
    }

    // List conversations for user (like messenger)
    @GetMapping("/conversations/{userId}")
    public List<ConversationDto> listConversations(@PathVariable Long userId) {
        return chatService.listConversations(userId);
    }
}
