package com.belstu.thesisproject.domain.workday;

import com.belstu.thesisproject.domain.user.Client;
import com.belstu.thesisproject.domain.user.Psychologist;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "event")
@NoArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "psychologist_id")
    private Psychologist psychologist;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "is_ended", nullable = false)
    private Boolean isEnded;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "reason_for_visit")
    private String reasonForVisit;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "feedback")
    private String feedback;

    @PrePersist
    public void onCreate() {
        isEnded = false;
        isConfirmed = true;
        roomId = UUID.randomUUID().toString();
    }

    @PreUpdate
    public void OnUpdate() {
        if (!isConfirmed) {
            roomId = "";
        }
    }
}
