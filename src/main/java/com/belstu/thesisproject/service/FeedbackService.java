package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback get(String id);
    List<Feedback> getAll();
    Feedback save(Feedback feedback);
    void delete(String id);
}
