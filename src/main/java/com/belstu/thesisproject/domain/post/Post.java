package com.belstu.thesisproject.domain.post;

import static java.time.LocalDate.now;

import com.belstu.thesisproject.domain.user.Psychologist;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "posts")
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", columnDefinition = "VARCHAR(255)")
  private String id;

  @Column(name = "title", length = 100, nullable = false)
  private String title;

  @Column(name = "text", columnDefinition = "TEXT", nullable = false)
  private String text;

  @Column(name = "post_date", nullable = false)
  private LocalDate postDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "psychologist_id")
  private Psychologist psychologist;

  @OneToMany(
      mappedBy = "post",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
  private Set<Comment> comments;

  @PrePersist
  protected void onCreate() {
    postDate = now();
  }
}
