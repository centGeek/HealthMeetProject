package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingRequestEntityMapper {

    @Mapping(target = "patient",ignore = true)
    @Mapping(target = "doctor", ignore = true)
    MeetingRequest mapFromEntity(final MeetingRequestEntity meetingRequestEntity);

    MeetingRequestEntity mapToEntity(final MeetingRequest meetingRequest);
}
