package com.belstu.thesisproject.dto.chat;

import com.belstu.thesisproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

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
