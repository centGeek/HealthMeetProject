package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.MeetingRequest;

import java.util.List;

public interface MeetingRequestDAO {
        List<MeetingRequest> findAvailable();
        List<MeetingRequest> findAllActiveMeetingRequests(String email);

        List<MeetingRequest> findByPatientEmail(String email);


        List<MeetingRequest> findEndedVisits();

    List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email);
}
