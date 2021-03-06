package com.belstu.thesisproject.domain.chat;

import static java.time.LocalDateTime.now;
import static javax.persistence.EnumType.STRING;

import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.chat.MessageStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "messages")
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", columnDefinition = "VARCHAR(255)")
  private String id;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id")
  private User sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipient_id")
  private User recipient;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id")
  private Chat chat;

  @Column(name = "text", columnDefinition = "TEXT", nullable = false)
  private String text;

  @Column(name = "status", nullable = false, length = 50, updatable = false)
  @Enumerated(STRING)
  private MessageStatus status;

  @PrePersist
  protected void onCreate() {
    creationDate = now();
  }
}
