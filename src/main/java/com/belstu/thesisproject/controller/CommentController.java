package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.dto.post.CommentDto;
import com.belstu.thesisproject.mapper.CommentMapper;
import com.belstu.thesisproject.service.impl.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{postId}")
    public List<CommentDto> getAllByPostId(@PathVariable String postId) {
        return commentService.getAll(postId).stream().map(commentMapper::map).collect(toList());
    }

    @PostMapping
    public CommentDto save(@RequestBody CommentDto commentDto) {
        return commentMapper.map(commentService.save(commentMapper.map(commentDto)));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(OK)
    public void delete(@PathVariable String commentId) {
        commentService.delete(commentId);
    }
}
