package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByPostId(String postId);
}
