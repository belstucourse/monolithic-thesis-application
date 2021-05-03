package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.chat.Message;

import javax.validation.constraints.NotNull;

public interface ChatService {
    Message saveMessage(@NotNull final Message message);
    Chat findById(@NotNull final String id);
    void validateChatState(@NotNull final String id);

    Chat create(@NotNull final Chat chat);
}
