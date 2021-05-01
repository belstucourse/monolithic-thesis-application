package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.user.Role;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.user.UserRole;
import javax.management.relation.RoleNotFoundException;
import javax.validation.constraints.NotNull;

public interface RoleService {
  Role findByName(@NotNull final UserRole userRole) throws RoleNotFoundException;

  void assignRoleToUser(@NotNull final User user);
}
