package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRequestDAO {
        List<MeetingRequest> findAvailable();

    
}
