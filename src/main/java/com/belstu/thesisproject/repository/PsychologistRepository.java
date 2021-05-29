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

    @Query(value = "SELECT DISTINCT ON (p.id) * FROM Psychologist p " +
            "JOIN psychologist_tag pt ON p.id = pt.psychologist_id " +
            "JOIN psycho_workday pw ON pw.psychologist_id = p.id " +
            "JOIN users u ON u.id = p.id " +
            "WHERE pt.tag_id IN ?1 AND (?2 BETWEEN pw.start_date_time AND pw.end_date_time OR ?3 BETWEEN pw.start_date_time AND pw.end_date_time)", nativeQuery = true)
    Page<Psychologist> findByTagsAndWorkdayDate(List<String> tagIds, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
