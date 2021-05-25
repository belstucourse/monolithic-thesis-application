package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    void deleteById(String id);
}
