package com.belstu.thesisproject.dto.user;

import com.belstu.thesisproject.valiadator.OnCreate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
  @NotBlank(groups = OnCreate.class)
  private String id;

  @NotBlank private String name;
}
