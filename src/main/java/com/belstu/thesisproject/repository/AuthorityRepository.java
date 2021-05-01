package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
  Optional<Authority> findByName(String name);
}
