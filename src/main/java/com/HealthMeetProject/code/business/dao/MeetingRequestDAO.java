package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRequestDAO {

    List<MeetingRequest> findAllActiveMeetingRequests(String email);

    List<MeetingRequest> findByPatientEmail(String email);


    List<MeetingRequest> findAllUpcomingVisitsByPatient(String email);

    List<MeetingRequest> findAllUpcomingCompletedVisitsByDoctor(String email);

    List<MeetingRequest> findAllEndedUpVisitsByDoctorAndPatient(String doctorEmail, String patientEmail);

    List<MeetingRequest> restFindAllEndedUpVisitsByDoctorAndPatient(String doctorEmail, String patientEmail);


    List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email);

    List<MeetingRequest> findAllActiveMeetingRequestsByDoctor(String email);

    List<MeetingRequest> restFindAllActiveMeetingRequestsByDoctor(String email);

    List<MeetingRequest> completedMeetingRequestsByDoctor(String email);

    boolean findIfMeetingRequestExistsWithTheSameDateAndDoctor(LocalDateTime since, LocalDateTime toWhen, Doctor doctor);

    void deleteById(Integer meetingId);

    MeetingRequest findById(Integer meetingId);
}
