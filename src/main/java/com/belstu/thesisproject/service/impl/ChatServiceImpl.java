package com.belstu.thesisproject.service.impl;

import static com.belstu.thesisproject.dto.chat.MessageStatus.RECEIVED;
import static java.lang.String.format;
import static java.util.Objects.isNull;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.dto.chat.ChatStatus;
import com.belstu.thesisproject.repository.ChatRepository;
import com.belstu.thesisproject.repository.MessageRepository;
import com.belstu.thesisproject.service.ChatService;
import com.belstu.thesisproject.service.UserService;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;

  private final UserService userService;

  @Transactional
  public Message saveMessage(@NotNull final Message message) {
    validateChatState(message.getId());
    validateUsersAccess(message.getSender().getId());
    message.setStatus(RECEIVED);
    return messageRepository.save(message);
  }

  @Override
  public Chat findById(@NotNull String id) {
    return chatRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException(format("Chat with %s id not found", id)));
  }

  @Override
  public void validateChatState(@NotNull String id) {
    final Chat chat = findById(id);
    if (isNull(chat) || !chat.getChatStatus().equals(ChatStatus.CONFIRMED)) {
      throw new IllegalArgumentException("Chat not exists or not confirmed");
    }
  }

  private void validateUsersAccess(final String chatId, final String... userIds) {
    final Chat chat = chatRepository.findById(chatId).orElseThrow(IllegalArgumentException::new);
    for (final String userId : userIds) {
      if (isUserHasNotAccess(chat, userId)) {
        throw new AccessDeniedException("Illegal access to chat");
      }
    }
  }

  private boolean isUserHasNotAccess(final Chat chat, final String userId) {
    return !chat.getClient().getId().equals(userId)
        || chat.getPsychologist().getId().equals(userId);
  }

  @Override
  public Chat create(@NotNull final Chat chat) {
    final String psychoId = chat.getPsychologist().getId();
    final String clientId = chat.getClient().getId();
    if (!userService.existsById(psychoId) && !userService.existsById(clientId)) {
      throw new IllegalArgumentException("User doesn't exists in application");
    }
    return chatRepository.save(chat);
  }

  @Override
  public Long countNewMessages(String senderId, String recipientId) {
    return messageRepository.countBySenderIdAndRecipientIdAndStatus(
        senderId, recipientId, RECEIVED);
  }

  @Override
  public Page<Message> findChatMessages(String clientId, String psychoId, Pageable pageable) {
    final Chat chat = chatRepository.findByClientIdAndPsychologistId(clientId, psychoId);
    return messageRepository.findByChatId(chat.getId(), pageable);
  }
}
