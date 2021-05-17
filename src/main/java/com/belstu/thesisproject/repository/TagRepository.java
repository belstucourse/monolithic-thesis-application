package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.Role;
import com.belstu.thesisproject.domain.user.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findByName(String name);
}
