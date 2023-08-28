package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class MeetingRequestService {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final MeetingRequestDAO meetingRequestDAO;


    public List<MeetingRequest> availableServiceRequests() {
        return meetingRequestDAO.findAvailable();
    }

    @Transactional
    public void makeMeetingRequest(Patient patient) {
        patientService.saveMeetingRequest(patient);
    }

    public void saveMeetingRequest(MeetingRequest request) {
        validate(request.getPatient().getEmail());
        MeetingRequest meetingServiceRequest = buildMeetingRequest(request);
        Set<MeetingRequest> existingMeetingServiceRequests = request.getPatient().getMeetingRequests();
        existingMeetingServiceRequests.add(meetingServiceRequest);
        patientService.saveMeetingRequest(request.getPatient().withMeetingRequests(existingMeetingServiceRequests));
    }


    private void validate(String email) {
        List<MeetingRequest> meetingRequests = meetingRequestDAO.findAllActiveMeetingRequests(email);
        if (meetingRequests.size() != 1) {
            throw new ProcessingException(
                    "There should be only one active meeting request at a time, patient email: [%s]".formatted(email));
        }
    }


    private MeetingRequest buildMeetingRequest(
            MeetingRequest request
    ) {
        OffsetDateTime now = OffsetDateTime.now();
        return MeetingRequest.builder()
                .meetingRequestNumber(generateMeetingRequestNumber(now))
                .completedDateTime(OffsetDateTime.now().toLocalDateTime())
                .description(request.getDescription())
                .patient(request.getPatient())
                .doctor(request.getDoctor())
                .build();
    }

    private String generateMeetingRequestNumber(OffsetDateTime when) {
        return "%s.%s.%s-%s.%s.%s.%s".formatted(
                when.getYear(),
                when.getMonth().ordinal(),
                when.getDayOfMonth(),
                when.getHour(),
                when.getMinute(),
                when.getSecond(),
                randomInt(10, 100)
        );
    }

    @SuppressWarnings("SameParameterValue")
    private int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @Transactional
    public MeetingRequest findAnyActiveServiceRequest(String email) {
        List<MeetingRequest> meetingRequests = meetingRequestDAO.findByPatientEmail(email);
        validate(email);
        return meetingRequests.stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Could not find any meeting requests, patient email: [%s]".formatted(email)));
    }
}