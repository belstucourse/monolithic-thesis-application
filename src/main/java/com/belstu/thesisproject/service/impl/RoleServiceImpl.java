package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.Role;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.user.UserRole;
import com.belstu.thesisproject.repository.RoleRepository;
import com.belstu.thesisproject.service.RoleService;
import java.util.Set;
import javax.management.relation.RoleNotFoundException;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Role findByName(@NotNull final UserRole userRole) throws RoleNotFoundException {
    return roleRepository.findByUserRole(userRole).orElseThrow(RoleNotFoundException::new);
  }

  @Override
  public void assignRoleToUser(@NotNull final User user) {
    final Set<UserRole> userRoles = user.getRoles();
    final Set<Role> persistedRoles = roleRepository.findAllByUserRole(userRoles);
    user.setRoles(persistedRoles);
  }
}
