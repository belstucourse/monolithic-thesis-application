package com.belstu.thesisproject.dto.post;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PostDto {
  private String id;
  private String title;
  private String text;
  private LocalDate postDate;
  private String psychologistId;
  private String photoUrl;
}
