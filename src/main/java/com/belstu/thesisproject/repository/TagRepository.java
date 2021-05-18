package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.Tag;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
  Optional<Tag> findByName(String name);
  List<Tag> findByNameIn(List<String> names);
}
