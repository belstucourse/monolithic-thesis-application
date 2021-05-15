package com.belstu.thesisproject.dto.post;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDto {
    private String id;
    private String title;
    private String text;
    private LocalDate postDate;
    private String psychologistId;
}
