package com.belstu.thesisproject.domain.user;

import static java.time.LocalDate.now;
import static org.hibernate.validator.internal.util.CollectionHelper.newHashSet;

import com.belstu.thesisproject.domain.chat.Message;
import com.belstu.thesisproject.updater.UserUpdateVisitor;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User<T extends User<T>> implements UserUpdateVisitor<T> {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", columnDefinition = "VARCHAR(255)")
  private String id;

  @Column(name = "first_name", length = 100, nullable = false)
  private String firstName;

  @Column(name = "middle_name", length = 100, nullable = false)
  private String middleName;

  @Column(name = "last_name", length = 100, nullable = false)
  private String lastName;

  @Column(name = "register_date", nullable = false, updatable = false)
  private LocalDate registerDate;

  @Column(name = "is_deactivated", nullable = false)
  private Boolean deactivated;

  @Column(name = "deactivated_date")
  private LocalDate deactivatedDate;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "email", unique = true, length = 100, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = newHashSet();

  @OneToMany(
      mappedBy = "sender",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Message> messages;

  @PrePersist
  protected void onCreate() {
    registerDate = now();
    deactivated = false;
  }

  public void addRole(@NotNull final Role role) {
    roles.add(role);
  }
}
