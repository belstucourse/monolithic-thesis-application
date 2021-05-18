package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PsychologistRepository extends JpaRepository<Psychologist, String> {
    Page<Psychologist> findDistinctByTagsIn(List<Tag> tags, Pageable pageable);
}
