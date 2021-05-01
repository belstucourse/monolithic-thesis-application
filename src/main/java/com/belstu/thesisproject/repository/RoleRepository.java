package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.Role;
import com.belstu.thesisproject.dto.user.UserRole;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByUserRole(UserRole userRole);

  Set<Role> findAllByUserRole(Set<UserRole> userRoles);
}
