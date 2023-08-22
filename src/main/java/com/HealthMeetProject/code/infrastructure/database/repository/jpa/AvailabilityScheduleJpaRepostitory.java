package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AvailabilityScheduleJpaRepostitory  extends JpaRepository<Integer, AvailabilityScheduleEntity> {
   

}
