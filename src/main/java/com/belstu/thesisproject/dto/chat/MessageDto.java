package com.belstu.thesisproject.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDto {
    private String id;

    private LocalDateTime creationDate;

    private String senderId;

    private String chatId;

    private String text;
}
