package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.dto.workday.EventDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "eventDto.psychologistId", target = "psychologist.id")
    @Mapping(source = "eventDto.clientId", target = "client.id")
    Event map(EventDto eventDto);

    @InheritInverseConfiguration
    EventDto map(Event event);
}
