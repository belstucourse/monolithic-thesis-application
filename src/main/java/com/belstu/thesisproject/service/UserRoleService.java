package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.user.User;
import javax.validation.constraints.NotNull;

public interface UserRoleService {
  void setUserRole(@NotNull final User user);
}
