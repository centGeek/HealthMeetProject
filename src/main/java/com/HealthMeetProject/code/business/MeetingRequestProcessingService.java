package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class MeetingRequestProcessingService {
    private final MeetingRequestService meetingRequestService;
    private final PatientService patientService;

    private final DoctorService doctorService;

    public List<Doctor> availableCars() {
        return doctorService.findAllAvailableDoctors();

    }


    @Transactional
    public MeetingRequest confirmationMeetingRequest(final MeetingRequest meetingRequest){
        return doctorService.confirmMeetingRequest(meetingRequest);
    }
    @Transactional
    public BigDecimal showPaymentForVisit(final MeetingRequest meetingRequest){
        BigDecimal salaryFor15minMeet = meetingRequest.getDoctor().getSalaryFor15minMeet();
        return salaryFor15minMeet;
    }

}
