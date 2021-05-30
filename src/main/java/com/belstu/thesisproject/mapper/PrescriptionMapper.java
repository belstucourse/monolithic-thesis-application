package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.workday.Prescription;
import com.belstu.thesisproject.dto.workday.PrescriptionDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    @Mapping(source = "dto.eventId", target = "event.id")
    Prescription map(PrescriptionDto dto);

    @InheritInverseConfiguration
    PrescriptionDto map(Prescription entity);
}
