package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.chat.Message;

import javax.validation.constraints.NotNull;

public interface ChatService {
    Message save(@NotNull final Message message);
}
