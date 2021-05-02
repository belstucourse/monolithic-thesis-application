package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.exception.PostNotFoundException;
import com.belstu.thesisproject.repository.PostRepository;
import com.belstu.thesisproject.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Override
    public Post getPostById(@NotNull String postId) throws PostNotFoundException {
        return postRepository.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public List<Post> getAllPostsByAuthorId(@NotNull String authorId, Pageable pageable) {
        return postRepository.findAllByAuthorId(authorId, pageable);
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post save(@NotNull Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(@NotNull Post post) throws PostNotFoundException {
        return null;
    }

    @Override
    public void delete(@NotNull String postId) throws PostNotFoundException {
        postRepository.deleteById(postId);
    }

    @Override
    public Post patch(@NotNull Post post) throws PostNotFoundException {
        return null;
    }
}
