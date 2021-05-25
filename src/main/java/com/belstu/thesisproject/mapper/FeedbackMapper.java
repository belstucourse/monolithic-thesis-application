package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.Feedback;
import com.belstu.thesisproject.dto.FeedbackDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(source = "dto.clientId", target = "client.id")
    Feedback map(FeedbackDto dto);

    @InheritInverseConfiguration
    FeedbackDto map(Feedback entity);

    List<Feedback> mapToEntityList(List<FeedbackDto> dtos);

    List<FeedbackDto> mapToDtoList(List<Feedback> entities);
}
