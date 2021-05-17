package com.belstu.thesisproject.dto.post;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDto {
  private String id;
  private String text;
  private LocalDateTime creationDate;
  private String senderId;
  private String postId;
}
