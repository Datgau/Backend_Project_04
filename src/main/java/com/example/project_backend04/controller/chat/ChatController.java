package com.example.project_backend04.controller.chat;

import com.example.project_backend04.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class ChatController {
    @MessageMapping("/chat")  // Client gửi lên /app/chat
    @SendTo("/topic/messages") // Server phát ra /topic/messages
    public ChatMessage send(ChatMessage message) {
        return message; // gửi lại cho tất cả client
    }
}
