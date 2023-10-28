package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorEntityMapper {
    @Mapping(source = "meetingRequests", target = "meetingRequests", ignore = true)
    Doctor mapFromEntity(DoctorEntity doctorEntity);

    @Mapping(source = "meetingRequests", target = "meetingRequests", ignore = true)
    DoctorEntity mapToEntity(Doctor doctor);
}
