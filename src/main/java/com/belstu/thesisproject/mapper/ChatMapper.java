package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.dto.chat.ChatDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "psychologistId", target = "psychologist.id")
    Chat map(ChatDto dto);

    @InheritInverseConfiguration
    ChatDto map(Chat entity);
}
