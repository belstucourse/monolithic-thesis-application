package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.dto.chat.ChatDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
  @Mapping(source = "chatDto.clientId", target = "client.id")
  @Mapping(source = "chatDto.psychologistId", target = "psychologist.id")
  @Mapping(source = "chatDto.clientName", target = "client.firstName")
  @Mapping(source = "chatDto.psychologistName", target = "psychologist.firstName")
  Chat map(ChatDto chatDto);

  @InheritInverseConfiguration
  ChatDto map(Chat chatEntity);
}
