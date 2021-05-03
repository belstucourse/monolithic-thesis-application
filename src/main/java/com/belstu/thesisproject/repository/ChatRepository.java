package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
