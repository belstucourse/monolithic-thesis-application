package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.post.Mark;
import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.domain.post.PostMarkResponse;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.exception.PostNotFoundException;
import com.belstu.thesisproject.repository.MarkRepository;
import com.belstu.thesisproject.repository.PostRepository;
import com.belstu.thesisproject.service.PostService;
import com.belstu.thesisproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static com.belstu.thesisproject.domain.post.MarkType.DISLIKE;
import static com.belstu.thesisproject.domain.post.MarkType.LIKE;
import static com.belstu.thesisproject.domain.post.MarkType.NOTHING;
import static com.belstu.thesisproject.service.CurrentUserEmailExtractor.getEmailOfCurrentUser;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MarkRepository markRepository;
    private final UserService userService;

    @Override
    public Post getPostById(@NotNull String postId) throws PostNotFoundException {
        return postRepository.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public List<Post> getAllPostsByAuthorId(@NotNull String authorId, Pageable pageable) {
        return postRepository.findAllByPsychologistId(authorId, pageable);
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
        return postRepository.save(post);
    }

    @Override
    public void delete(@NotNull String postId) throws PostNotFoundException {
        postRepository.deleteById(postId);
    }

    @Override
    public Post patch(@NotNull Post post) throws PostNotFoundException {
        return null;
    }

    @Override
    public Mark saveMark(@NotNull final Mark mark) {
        final Mark userMark = markRepository.findByPostIdAndUserId(mark.getPostId(), mark.getUserId()).orElse(mark);
        if (nonNull(userMark.getId()) && userMark.getMarkType() == mark.getMarkType()) {
            throw new IllegalArgumentException("Вы не можете добавить новый лайк");
        }
        userMark.setMarkType(mark.getMarkType());
        return markRepository.save(userMark);
    }

    @Override
    @Transactional
    public PostMarkResponse getPostMarks(String postId) {
        final String emailOfCurrentUser = getEmailOfCurrentUser();
        final User currentUser = userService.getUserByEmail(emailOfCurrentUser);
        final long countLikes = markRepository.countByPostIdAndMarkType(postId, LIKE);
        final long countDislikes = markRepository.countByPostIdAndMarkType(postId, DISLIKE);
        final Optional<Mark> markOpt = markRepository.findByPostIdAndUserId(postId, currentUser.getId());
        final PostMarkResponse postMarkResponse = PostMarkResponse.builder()
                .likeCount(countLikes)
                .userMarkType(NOTHING)
                .dislikeCount(countDislikes)
                .build();
        markOpt.ifPresent((mark) -> postMarkResponse.setUserMarkType(mark.getMarkType()));
        return postMarkResponse;
    }
}
