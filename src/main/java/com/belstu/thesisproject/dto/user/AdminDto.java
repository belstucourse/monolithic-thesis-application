package com.belstu.thesisproject.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import java.util.Set;
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
@JsonTypeName("admin")
public class AdminDto extends UserDto {
  public AdminDto(
      String id,
      String firstName,
      String middleName,
      String lastName,
      LocalDate registerDate,
      Boolean deactivated,
      LocalDate deactivatedDate,
      String imageUrl,
      String email,
      String password,
      Set<RoleDto> roles) {
    super(
        id,
        firstName,
        middleName,
        lastName,
        registerDate,
        deactivated,
        deactivatedDate,
        imageUrl,
        email,
        password,
        roles);
  }
}
