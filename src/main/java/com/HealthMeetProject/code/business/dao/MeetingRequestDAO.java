package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;

import java.time.OffsetDateTime;
import java.util.List;

public interface MeetingRequestDAO {
    List<MeetingRequest> findAvailable();

    List<MeetingRequest> findAllActiveMeetingRequests(String email);

    List<MeetingRequest> findByPatientEmail(String email);


     List<MeetingRequest> findAllUpcomingVisits(String email);

    List<MeetingRequest> findEndedVisits();

    List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email);

    List<MeetingRequest> availableServiceRequests(String email);

    List<MeetingRequest> availableEndedVisitsByDoctor(String email);

    boolean findIfMeetingRequestExistsWithTheSameDateAndDoctor(OffsetDateTime since, OffsetDateTime toWhen, Doctor doctor);

    void deleteById(Integer meetingId);
    MeetingRequest findById(Integer meetingId);
}
