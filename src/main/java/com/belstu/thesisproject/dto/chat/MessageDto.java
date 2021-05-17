package com.belstu.thesisproject.dto.chat;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MessageDto {
  private String id;

  private LocalDateTime creationDate;

  private String senderId;

  private String chatId;

  private String text;

  private String recipientId;

  private MessageStatus status;
}
