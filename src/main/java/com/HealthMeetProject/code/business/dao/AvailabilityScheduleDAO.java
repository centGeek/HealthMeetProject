package com.HealthMeetProject.code.business.dao;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;

import java.time.OffsetDateTime;
import java.util.List;


public interface AvailabilityScheduleDAO  {
    List<AvailabilitySchedule> findAllTermsByGivenDoctor(String email);

    AvailabilitySchedule addTerm(OffsetDateTime since, OffsetDateTime toWhen, DoctorEntity doctorEntity);
}
