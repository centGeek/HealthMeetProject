package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AvailabilityScheduleMapper {
    AvailabilityScheduleDTO map(AvailabilitySchedule availabilitySchedule);
}
