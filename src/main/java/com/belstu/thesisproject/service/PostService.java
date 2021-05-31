package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.post.Mark;
import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.domain.post.PostMarkResponse;
import com.belstu.thesisproject.exception.AuthorNotFoundException;
import com.belstu.thesisproject.exception.PostNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
  Post getPostById(@NotNull String postId) throws PostNotFoundException;

  List<Post> getAllPostsByAuthorId(@NotNull String authorId, Pageable pageable)
      throws AuthorNotFoundException;

  Page<Post> getAllPosts(Pageable pageable);

  Post save(@NotNull final Post post);

  Post update(@NotNull final Post post) throws PostNotFoundException;

  void delete(@NotNull final String postId) throws PostNotFoundException;

  Post patch(@NotNull final Post post) throws PostNotFoundException;

  Mark saveMark(@NotNull final Mark mark);

  PostMarkResponse getPostMarks(String postId);
}
