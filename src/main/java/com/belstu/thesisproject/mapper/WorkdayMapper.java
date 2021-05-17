package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoWorkdayDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkdayMapper {
  @Mapping(source = "psychoWorkdayDto.psychologistId", target = "psychologist.id")
  PsychoWorkday map(PsychoWorkdayDto psychoWorkdayDto);

  @InheritInverseConfiguration
  PsychoWorkdayDto map(PsychoWorkday psychoWorkday);
}
