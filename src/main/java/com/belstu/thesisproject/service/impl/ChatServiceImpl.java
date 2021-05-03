package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final
    @Override
    public Message save(@NotNull final Message message) {
        return null;
    }
}
