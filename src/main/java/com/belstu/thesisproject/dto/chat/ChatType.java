package com.belstu.thesisproject.dto.chat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatType {
    VIDEO(15),
    CHAT(10),
    IMMEDIATE_CHAT(20);

    private final Integer price;
}
