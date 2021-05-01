package com.belstu.thesisproject.config;

import com.belstu.thesisproject.domain.user.Authority;
import com.belstu.thesisproject.domain.user.Role;
import com.belstu.thesisproject.repository.AuthorityRepository;
import com.belstu.thesisproject.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetupRoleDBConfig {
  private final CSVDataLoader csvDataLoader;
  private static final String AUTHORITY_FILE_PATH = "authorities.csv";
  private static final String ROLES_FILE = "roles.csv";
  private static final String ROLES_AUTHORITIES_FILE = "authorities-roles.csv";

  private final RoleRepository roleRepository;
  private final AuthorityRepository authorityRepository;

  public List<Role> getRoles() {
    final List<Authority> authorities = getAuthorities();
    final List<Role> roles = csvDataLoader.loadObjectList(Role.class, ROLES_FILE);
    final List<String[]> rolesAuthorities =
        csvDataLoader.loadManyToManyRelationship(ROLES_AUTHORITIES_FILE);

    for (String[] roleAuthority : rolesAuthorities) {
      final Role role = findRoleByName(roles, roleAuthority[0]);
      Set<Authority> authoritySet = role.getAuthorities();
      if (authoritySet == null) {
        authoritySet = new HashSet<>();
      }
      authoritySet.add(findAuthorityByName(authorities, roleAuthority[1]));
      role.setAuthorities(authoritySet);
    }
    return roles;
  }

  public List<Authority> getAuthorities() {
    return csvDataLoader.loadObjectList(Authority.class, AUTHORITY_FILE_PATH);
  }

  private Role findRoleByName(List<Role> roles, String roleName) {
    return roles.stream()
        .filter(item -> item.getUserRole().name().equals(roleName))
        .findFirst()
        .orElseGet(Role::new);
  }

  private Authority findAuthorityByName(
      final List<Authority> authorities, final String authorityName) {
    return authorities.stream()
        .filter(item -> item.getName().equals(authorityName))
        .findFirst()
        .orElseGet(Authority::new);
  }

  private void setupRolesAndAuthorities() {
    final List<Authority> authorities = getAuthorities();
    for (Authority authority : authorities) {
      setupAuthorities(authority);
    }

    List<Role> roles = getRoles();
    for (Role role : roles) {
      setupRole(role);
    }
  }

  public void setupAuthorities(final Authority authority) {
    authorityRepository
        .findByName(authority.getName())
        .orElseGet(() -> authorityRepository.save(authority));
  }

  public void setupRole(final Role role) {
    roleRepository.findByUserRole(role.getUserRole()).orElseGet(() -> prepareRole(role));
  }

  public Role prepareRole(final Role role) {
    Set<Authority> authorities = role.getAuthorities();
    Set<Authority> persistedAuthorities = new HashSet<>();
    for (Authority authority : authorities) {
      persistedAuthorities.add(
          authorityRepository.findByName(authority.getName()).orElseGet(Authority::new));
    }
    role.setAuthorities(persistedAuthorities);
    return roleRepository.save(role);
  }

  @PostConstruct
  @Transactional
  public void setupData() {
    setupRolesAndAuthorities();
  }
}
