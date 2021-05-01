package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.user.Authority;
import javax.management.relation.RoleNotFoundException;
import javax.validation.constraints.NotNull;

public interface AuthorityService {
  Authority findByName(@NotNull final String name) throws RoleNotFoundException;

  Authority saveAuthority(@NotNull final Authority authority);
}
