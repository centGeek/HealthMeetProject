package com.HealthMeetProject.code.business.dao;

import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AvailabilityScheduleDAO  {
    List<AvailabilitySchedule> findAllTermsByGivenDoctor(String email);
    List<AvailabilitySchedule> findAllAvailableTerms(@Param("email") String email);
    List<AvailabilitySchedule> showDoctorCalendar(@Param("email") String email);


}
