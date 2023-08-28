package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRequestDAO {
        List<MeetingRequest> findAvailable();
        List<MeetingRequest> findByPatientEmail(String email);
        List<MeetingRequest> findAllActiveMeetingRequests(String email);


}
