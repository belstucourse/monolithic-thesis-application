package com.belstu.thesisproject.domain.user;

import com.belstu.thesisproject.domain.post.Event;
import com.belstu.thesisproject.updater.UserUpdater;
import java.util.Set;
import javax.persistence.CascadeType;
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

  @Override
  public Client update(UserUpdater userUpdater, Client newUser) {
    return userUpdater.update(this, newUser);
  }
}
