package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRequestJpaRepostitory  extends JpaRepository<Integer, MeetingRequestEntity> {
    
}
