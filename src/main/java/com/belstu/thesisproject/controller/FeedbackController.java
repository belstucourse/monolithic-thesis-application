package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.Feedback;
import com.belstu.thesisproject.dto.FeedbackDto;
import com.belstu.thesisproject.mapper.FeedbackMapper;
import com.belstu.thesisproject.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/feedbacks")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @GetMapping("/{id}")
    public FeedbackDto getFeedbackById(@PathVariable String id) {
        return feedbackMapper.map(feedbackService.get(id));
    }

    @GetMapping
    public List<FeedbackDto> getAll() {
        return feedbackMapper.mapToDtoList(feedbackService.getAll());
    }

    @PostMapping
    public FeedbackDto save(@RequestBody FeedbackDto feedback) {
        final Feedback feedbackEntity = feedbackMapper.map(feedback);
        return feedbackMapper.map(feedbackService.save(feedbackEntity));
    }

    @DeleteMapping
    @ResponseStatus(OK)
    public void delete(String id) {
        feedbackService.delete(id);
    }
}
