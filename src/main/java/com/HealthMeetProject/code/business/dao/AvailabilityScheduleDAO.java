package com.HealthMeetProject.code.business.dao;

import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AvailabilityScheduleDAO  {
    List<AvailabilitySchedule> findAllTermsByGivenDoctor(String email);

}
