package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.dto.chat.ChatDto;
import com.belstu.thesisproject.dto.chat.MessageDto;
import com.belstu.thesisproject.mapper.ChatMapper;
import com.belstu.thesisproject.mapper.MessageMapper;
import com.belstu.thesisproject.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {
  private final ChatService chatService;
  private final MessageMapper messageMapper;
  private final ChatMapper chatMapper;

  @MessageMapping("/messages")
  @SendTo("/topic/messages")
  public MessageDto saveMessage(MessageDto message) {
    final Message savedMessage = chatService.saveMessage(messageMapper.map(message));
    return messageMapper.map(savedMessage);
  }

  @PostMapping("/api/chats")
  public ChatDto createChat(ChatDto chatDto) {
    final Chat chat = chatService.create(chatMapper.map(chatDto));
    return chatMapper.map(chat);
  }
}
