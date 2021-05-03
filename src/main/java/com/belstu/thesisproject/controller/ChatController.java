package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.dto.chat.MessageDto;
import com.belstu.thesisproject.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/messages")
    @SendTo("/topic/greetings")
    public MessageDto saveMessage(MessageDto message)

    }
}
