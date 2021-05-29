package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.dto.workday.DetailedEventDto;
import com.belstu.thesisproject.domain.workday.Event;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailedEventMapper {
    @Mapping(source = "eventDto.psychologistId", target = "psychologist.id")
    @Mapping(source = "eventDto.clientId", target = "client.id")
    Event map(DetailedEventDto eventDto);

    @InheritInverseConfiguration
    DetailedEventDto map(Event event);

    List<Event> mapToEntityList(List<DetailedEventDto> dtos);

    List<DetailedEventDto> mapToDtoList(List<Event> entities);
}
