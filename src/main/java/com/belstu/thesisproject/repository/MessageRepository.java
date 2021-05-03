package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
