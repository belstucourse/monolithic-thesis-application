package com.belstu.thesisproject.domain.user;

import com.belstu.thesisproject.updater.UserUpdater;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
  @OneToMany(
      mappedBy = "admin",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Support> supports;

  @Override
  public Admin update(UserUpdater userUpdater, Admin newUser) {
    return userUpdater.update(this, newUser);
  }
}
