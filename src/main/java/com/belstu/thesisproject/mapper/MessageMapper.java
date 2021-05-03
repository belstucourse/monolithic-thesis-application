package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.dto.chat.MessageDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(source="senderId",target = "sender.id")
    @Mapping(source="chatId",target = "chat.id")
    Message map(MessageDto dto);

    @InheritInverseConfiguration
    MessageDto map(Message entity);
}
