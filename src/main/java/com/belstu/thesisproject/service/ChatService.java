package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.chat.Message;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {
  Message saveMessage(@NotNull final Message message);

  Chat findById(@NotNull final String id);

  void validateChatState(@NotNull final String id);

  Chat create(@NotNull final Chat chat);

  Long countNewMessages(String senderId, String recipientId);

  Page<Message> findChatMessages(String senderId, String recipientId, Pageable pageable);
}
