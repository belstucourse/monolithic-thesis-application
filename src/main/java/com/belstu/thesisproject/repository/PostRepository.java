package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
  Optional<Post> getPostById(final String postId);
  List<Post> findAllByPsychologistId(final String authorId, final Pageable pageable);
  Page<Post> findAll(Pageable pageable);
}
