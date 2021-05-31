package com.belstu.thesisproject.domain.user;

import com.belstu.thesisproject.updater.UserUpdater;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "admin")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Admin extends User<Admin> {
  private LocalDate localDate;
  @OneToMany(
      mappedBy = "admin",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Support> supports;

  @PrePersist
  public void onCreate() {
    localDate = LocalDate.now();
  }

  @Override
  public Admin update(UserUpdater userUpdater, Admin newUser) {
    return userUpdater.update(this, newUser);
  }
}
