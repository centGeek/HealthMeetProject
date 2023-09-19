package com.HealthMeetProject.code.business.dao;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;

import java.time.LocalDateTime;
import java.util.List;


public interface AvailabilityScheduleDAO {
    List<AvailabilitySchedule> findAllTermsByGivenDoctor(String email);

    AvailabilitySchedule addTerm(LocalDateTime since, LocalDateTime toWhen, DoctorEntity doctorEntity);

    List<AvailabilitySchedule> findAllAvailableTermsByGivenDoctor(String email);

    AvailabilitySchedule findById(Integer id);

    void deleteById(Integer availabilityScheduleId);

    void save(AvailabilityScheduleEntity availabilityScheduleEntity);

    List<AvailabilityScheduleDTO> findAll(); 
}
