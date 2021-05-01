package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.Authority;
import com.belstu.thesisproject.repository.AuthorityRepository;
import com.belstu.thesisproject.service.AuthorityService;
import javax.management.relation.RoleNotFoundException;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
  private final AuthorityRepository authorityRepository;

  @Override
  public Authority findByName(@NotNull final String name) throws RoleNotFoundException {
    return authorityRepository.findByName(name).orElseThrow(RoleNotFoundException::new);
  }

  @Override
  public Authority saveAuthority(@NotNull final Authority authority) {
    return authorityRepository.save(authority);
  }
}
