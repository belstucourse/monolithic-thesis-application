package com.belstu.thesisproject.domain.user;

import static javax.persistence.EnumType.STRING;

import com.belstu.thesisproject.dto.user.UserRole;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "roles")
@Data
public class Role {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", columnDefinition = "VARCHAR(255)")
  private String id;

  @Column(name = "user_role", nullable = false, length = 50, unique = true, updatable = false)
  @Enumerated(STRING)
  private UserRole userRole;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(
      mappedBy = "roles",
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  private Set<User> users;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(
      mappedBy = "roles",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Authority> authorities;
}
