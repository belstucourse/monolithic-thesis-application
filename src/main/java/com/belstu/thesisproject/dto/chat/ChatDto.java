package com.belstu.thesisproject.dto.chat;

import lombok.Data;

@Data
public class ChatDto {
  private String id;

  private String clientId;

  private String psychologistId;

  private String clientName;

  private String psychologistName;

  private ChatStatus chatStatus;

  private ChatType chatType;
}
