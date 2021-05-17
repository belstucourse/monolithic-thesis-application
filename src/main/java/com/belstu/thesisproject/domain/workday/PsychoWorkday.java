package com.belstu.thesisproject.domain.workday;

import com.belstu.thesisproject.domain.user.Psychologist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "psycho_workday")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class PsychoWorkday {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", columnDefinition = "VARCHAR(255)")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "psychologist_id")
  private Psychologist psychologist;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "start_date_time", nullable = false)
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time", nullable = false)
  private LocalDateTime endDateTime;
}
