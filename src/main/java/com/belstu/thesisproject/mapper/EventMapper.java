package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.dto.post.PostDto;
import com.belstu.thesisproject.dto.workday.EventDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "eventDto.psychologistId", target = "psychologist.id")
    @Mapping(source = "eventDto.clientId", target = "client.id")
    Event map(EventDto eventDto);

    @InheritInverseConfiguration
    EventDto map(Event event);

    List<Event> mapToEntityList(List<EventDto> dtos);

    List<EventDto> mapToDtoList(List<Event> entities);
}
