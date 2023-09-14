package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.domain.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface PatientMapper {
    PatientDTO map(Patient patient);
}
