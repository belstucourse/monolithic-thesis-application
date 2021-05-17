package com.belstu.thesisproject.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@JsonTypeName("client")
public class ClientDto extends UserDto {
  private LocalDate birthdayDate;
  private String avatarUrl;
}
