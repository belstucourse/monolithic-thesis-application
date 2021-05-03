package com.belstu.thesisproject.domain.chat;

import com.belstu.thesisproject.domain.user.Client;
import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.dto.chat.ChatStatus;
import com.belstu.thesisproject.dto.chat.ChatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.EnumType.STRING;

@Entity(name = "chats")
@Table(name = "chats")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Chat {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "psychologist_id")
    private Psychologist psychologist;

    @Column(name = "chat_status", nullable = false, length = 50)
    @Enumerated(STRING)
    private ChatStatus chatStatus;

    @Column(name = "chat_type", nullable = false, length = 50)
    @Enumerated(STRING)
    private ChatType chatType;

    @OneToMany(
            mappedBy = "chat",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Message> messages;
}
