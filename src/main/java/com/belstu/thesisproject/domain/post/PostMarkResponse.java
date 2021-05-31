package com.belstu.thesisproject.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMarkResponse {
    private Long likeCount;
    private Long dislikeCount;
    private MarkType userMarkType;
}
