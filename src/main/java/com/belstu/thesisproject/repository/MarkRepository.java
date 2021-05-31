package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.post.Mark;
import com.belstu.thesisproject.domain.post.MarkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, String> {
    List<Mark> findByPostId(String postId);
    Optional<Mark> findByPostIdAndUserId(String postId, String userId);
    long countByPostIdAndMarkType(String postId, MarkType markType);
    Optional<Mark> findByPostIdAndUserIdAndMarkType(String postId, String userId, MarkType markType);
}
