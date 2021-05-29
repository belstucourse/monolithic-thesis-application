package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.workday.PsychoEventNotes;
import com.belstu.thesisproject.dto.workday.PsychoEventNotesDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PsychoEventNotesMapper {
    @Mapping(source = "dto.eventId", target = "event.id")
    PsychoEventNotes map(PsychoEventNotesDto dto);

    @InheritInverseConfiguration
    PsychoEventNotesDto map(PsychoEventNotes entity);
}
