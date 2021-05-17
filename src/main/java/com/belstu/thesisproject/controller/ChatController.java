package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.dto.chat.ChatDto;
import com.belstu.thesisproject.dto.chat.ChatNotification;
import com.belstu.thesisproject.dto.chat.MessageDto;
import com.belstu.thesisproject.mapper.ChatMapper;
import com.belstu.thesisproject.mapper.MessageMapper;
import com.belstu.thesisproject.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class ChatController {
  private final ChatService chatService;
  private final MessageMapper messageMapper;
  private final ChatMapper chatMapper;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat")
  public void saveMessage(@Payload MessageDto messageDto) {
    final Message savedMessage = chatService.saveMessage(messageMapper.map(messageDto));
    messagingTemplate.convertAndSendToUser(
        messageDto.getRecipientId(),
        "/queue/messages",
        new ChatNotification(
            savedMessage.getId(),
            savedMessage.getSender().getId(),
            savedMessage.getSender().getFirstName()));
  }

  @GetMapping("/messages/{senderId}/{recipientId}/count")
  public ResponseEntity<Long> countNewMessages(
      @PathVariable String senderId, @PathVariable String recipientId) {

    return ResponseEntity.ok(chatService.countNewMessages(senderId, recipientId));
  }

  @PostMapping("/api/chats")
  @ResponseBody
  public ChatDto createChat(ChatDto chatDto) {
    final Chat chat = chatService.create(chatMapper.map(chatDto));
    return chatMapper.map(chat);
  }

  @GetMapping("/messages/{clientId}/{psychoId}")
  public ResponseEntity<?> findChatMessages(
      @PathVariable String clientId, @PathVariable String psychoId, Pageable pageable) {
    return ResponseEntity.ok(chatService.findChatMessages(clientId, psychoId, pageable));
  }
}
