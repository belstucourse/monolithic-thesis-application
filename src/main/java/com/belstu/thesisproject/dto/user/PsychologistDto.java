package com.belstu.thesisproject.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@JsonTypeName("psychologist")
public class PsychologistDto extends UserDto {
  private Boolean verified;

  @PastOrPresent private LocalDate verifiedDate;

  @NotBlank private String mobile;

  private String avatarUrl;

  private Set<TagDto> tags;
}
