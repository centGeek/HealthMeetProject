package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AvailabilityScheduleEntityMapper {
     @Mapping(source = "doctor", target = "doctor", ignore = true)
     AvailabilitySchedule mapFromEntity(AvailabilityScheduleEntity availabilityScheduleEntity);
     @Mapping(source = "doctor", target = "doctor", ignore = true)

     AvailabilityScheduleEntity mapToEntity(AvailabilitySchedule availability);
}
