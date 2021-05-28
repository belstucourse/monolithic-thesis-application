package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PsychologistRepository extends JpaRepository<Psychologist, String> {
    Page<Psychologist> findDistinctByTagsIn(List<Tag> tags, Pageable pageable);

    @Query(value = "SELECT * FROM Psychologist p " +
            "JOIN psychologist_tag pt ON p.id = pt.psychologist_id " +
            "JOIN psycho_workday pw ON pw.psychologist_id = p.id " +
            "WHERE pt.tag_id IN ?1 AND pw.start_date_time >= ?2 " +
            "AND pw.end_date_time <= ?3", nativeQuery = true)
    Page<Psychologist> findByTagsAndWorkdayDate(List<String> tagIds, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
