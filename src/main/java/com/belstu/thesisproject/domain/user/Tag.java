package com.belstu.thesisproject.domain.user;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "tag")
@Data
public class Tag {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", columnDefinition = "VARCHAR(255)")
  private String id;

  @Column(name = "name", nullable = false, length = 50, unique = true)
  private String name;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(
      mappedBy = "tags",
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
      fetch = FetchType.LAZY)
  private Set<Psychologist> psychologists;
}
