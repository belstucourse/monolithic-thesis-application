package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.Feedback;
import com.belstu.thesisproject.exception.NotFoundException;
import com.belstu.thesisproject.repository.FeedbackRepository;
import com.belstu.thesisproject.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback get(String id) {
        return feedbackRepository.findById(id).orElseThrow(()->new NotFoundException("Feedback not found"));
    }

    @Override
    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    @Override
    @Transactional
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void delete(String id) {
        feedbackRepository.deleteById(id);
    }
}
