package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(final String email);
}
