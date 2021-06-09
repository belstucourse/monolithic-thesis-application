package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.post.Comment;
import com.belstu.thesisproject.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getAll(String postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(String id) {
        commentRepository.deleteById(id);
    }
}
