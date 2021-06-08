package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.post.Mark;
import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.domain.post.PostMarkResponse;
import com.belstu.thesisproject.dto.post.PostDto;
import com.belstu.thesisproject.exception.AuthorNotFoundException;
import com.belstu.thesisproject.exception.PostNotFoundException;
import com.belstu.thesisproject.mapper.PostMapper;
import com.belstu.thesisproject.repository.MarkRepository;
import com.belstu.thesisproject.service.PostService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.belstu.thesisproject.service.CurrentUserEmailExtractor.getEmailOfCurrentUser;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/posts")
@Validated
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable final String id) throws PostNotFoundException {
        return postMapper.map(postService.getPostById(id));
    }

    @GetMapping("/psycho/{id}")
    public List<PostDto> getPostByAuthorId(
            @PathVariable final String id,
            @PageableDefault(
                    sort = {"id"},
                    direction = DESC)
                    Pageable pageable)
            throws AuthorNotFoundException {
        return postMapper.mapToDtoList(postService.getAllPostsByAuthorId(id, pageable));
    }

    @GetMapping
    public Page<PostDto> getAllPosts(
            @PageableDefault(
                    sort = {"id"},
                    direction = DESC)
                    Pageable pageable) {
        final Page<Post> posts = postService.getAllPosts(pageable);
        final Page<PostDto> postDtos = posts.map(postMapper::map);
        return postDtos;
    }

    @PostMapping
    public PostDto savePost(@RequestBody final PostDto postDto) {
        final Post post = postMapper.map(postDto);
        return postMapper.map(postService.save(post));
    }

    @SneakyThrows
    @PutMapping
    public PostDto updatePost(@RequestBody final PostDto postDto) {
        final Post post = postMapper.map(postDto);
        return postMapper.map(postService.update(post));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = OK)
    public void deletePost(@PathVariable final String id) throws PostNotFoundException {
        final Post post = postService.getPostById(id);
        final String currentEmail = getEmailOfCurrentUser();
        if (!currentEmail.equals(post.getPsychologist().getEmail())) {
            throw new AccessDeniedException("Delete operation not permitted");
        }
        postService.delete(id);
    }

    @PostMapping("/marks")
    public Mark saveMark(@RequestBody final Mark mark) {
        return postService.saveMark(mark);
    }

    @GetMapping("/marks/{postId}")
    public PostMarkResponse getMarksPost(@PathVariable final String postId) {
        return postService.getPostMarks(postId);
    }

}
