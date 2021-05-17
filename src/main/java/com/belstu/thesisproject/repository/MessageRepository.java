package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.dto.chat.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
  long countBySenderIdAndRecipientIdAndStatus(
      String senderId, String recipientId, MessageStatus status);

  Page<Message> findByChatId(String chatId, Pageable pageable);
}
