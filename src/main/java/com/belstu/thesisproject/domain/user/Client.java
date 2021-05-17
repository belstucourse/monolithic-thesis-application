package com.belstu.thesisproject.domain.user;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.updater.UserUpdater;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "client")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Client extends User<Client> {
  @Column(name = "birthday_date", nullable = false)
  private LocalDate birthdayDate;

  @Column(name = "avatar_url", length = 100)
  private String avatarUrl;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(
      mappedBy = "clients",
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  private Set<Psychologist> psychologists;

  @OneToMany(
      mappedBy = "client",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Event> events;

  @OneToMany(
      mappedBy = "client",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Chat> chats;

  @Override
  public Client update(UserUpdater userUpdater, Client newUser) {
    return userUpdater.update(this, newUser);
  }
}
