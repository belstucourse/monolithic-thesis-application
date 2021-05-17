package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.dto.chat.MessageDto;
import com.belstu.thesisproject.exception.UserNotFoundException;
import com.belstu.thesisproject.service.UserService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserService.class)
public abstract class MessageMapper {
  protected UserService userService;

  @Mapping(source = "messageDto.chatId", target = "chat.id")
  public abstract Message map(MessageDto messageDto);

  @InheritInverseConfiguration
  public abstract MessageDto map(Message entity);

  @BeforeMapping
  protected void convertUserToId(Message message, @MappingTarget MessageDto messageDto) {
    messageDto.setSenderId(message.getSender().getId());
    messageDto.setRecipientId(message.getRecipient().getId());
  }

  @BeforeMapping
  protected void convertUserToId(MessageDto messageDto, @MappingTarget Message message) {
    final String senderId = messageDto.getSenderId();
    final String recipientId = messageDto.getRecipientId();
    try {
      message.setSender(userService.getUserById(senderId));
      message.setRecipient(userService.getUserById(recipientId));
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
  }
}
