package com.belstu.thesisproject.violation;

import static java.util.Collections.emptyList;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ValidationErrorResponse {
  private List<Violation> violations = emptyList();
}
