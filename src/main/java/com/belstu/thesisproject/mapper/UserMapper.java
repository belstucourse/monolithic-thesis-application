package com.belstu.thesisproject.mapper;

import static com.belstu.thesisproject.mapper.MapperErrorMessageKeys.CAST_EXCEPTION_MESSAGE_KEY;
import static java.lang.String.format;

import com.belstu.thesisproject.domain.user.Admin;
import com.belstu.thesisproject.domain.user.Client;
import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.Support;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.user.AdminDto;
import com.belstu.thesisproject.dto.user.ClientDto;
import com.belstu.thesisproject.dto.user.PsychologistDto;
import com.belstu.thesisproject.dto.user.SupportDto;
import com.belstu.thesisproject.dto.user.UserDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
  Client map(ClientDto dto);

  @InheritInverseConfiguration
  ClientDto map(Client entity);

  Support map(SupportDto dto);

  @InheritInverseConfiguration
  SupportDto map(Support entity);

  Psychologist map(PsychologistDto dto);

  @InheritInverseConfiguration
  PsychologistDto map(Psychologist entity);

  Admin map(AdminDto dto);

  @InheritInverseConfiguration
  AdminDto map(Admin entity);

  List<UserDto> mapToDtoList(List<User> allUsers);

  List<PsychologistDto> mapToDtoListP(List<Psychologist> allUsers);

  @InheritConfiguration
  default UserDto map(final User entity) {
    if (entity instanceof Client) {
      return map((Client) entity);
    } else if (entity instanceof Admin) {
      return map((Admin) entity);
    } else if (entity instanceof Psychologist) {
      return map((Psychologist) entity);
    } else if (entity instanceof Support) {
      return map((Support) entity);
    } else {
      throw new ClassCastException(
          format(CAST_EXCEPTION_MESSAGE_KEY, entity.getClass(), entity.getId()));
    }
  }

  @InheritInverseConfiguration
  default User map(final UserDto dto) {
    if (dto instanceof ClientDto) {
      return map((ClientDto) dto);
    } else if (dto instanceof AdminDto) {
      return map((AdminDto) dto);
    } else if (dto instanceof PsychologistDto) {
      return map((PsychologistDto) dto);
    } else if (dto instanceof SupportDto) {
      return map((SupportDto) dto);
    } else {
      throw new ClassCastException(format(CAST_EXCEPTION_MESSAGE_KEY, dto.getClass(), dto.getId()));
    }
  }


}
