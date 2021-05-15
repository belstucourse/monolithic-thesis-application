package com.belstu.thesisproject.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String id;
    private String text;
    private LocalDateTime creationDate;
    private String senderId;
    private String postId;
}
