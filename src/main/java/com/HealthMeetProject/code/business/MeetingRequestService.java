package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class MeetingRequestService {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final MeetingRequestDAO meetingRequestDAO;
    private final MeetingRequestJpaRepository meetingRequestJpaRepository;


    public List<MeetingRequest> availableServiceRequests() {
        return meetingRequestDAO.findAvailable();
    }  public List<MeetingRequest> availableEndedVisits() {
        return meetingRequestDAO.findEndedVisits();
    }


    @Transactional
    public void makeMeetingRequest(Patient patient, Doctor doctor, String description) {
        validate(patient.getEmail());
        MeetingRequest meetingServiceRequest = buildMeetingRequest(patient,doctor, description);
        patientService.saveMeetingRequest(meetingServiceRequest, patient);
    }


    private void validate(String email) {
        List<MeetingRequest> meetingRequests = meetingRequestDAO.findAllActiveMeetingRequests(email);
        if (meetingRequests.size() != 0) {
            throw new ProcessingException(
                    "There should be only one active meeting request at a time, patient email: [%s]".formatted(email));
        }
    }


    private MeetingRequest buildMeetingRequest(
            Patient patient,
            Doctor doctor,
            String description
    ) {
        OffsetDateTime now = OffsetDateTime.now();
        return MeetingRequest.builder()
                .meetingRequestNumber(generateMeetingRequestNumber(now))
                .receivedDateTime(OffsetDateTime.now().toLocalDateTime())
                .description(description)
                .patient(patient)
                .doctor(doctor)
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
                .orElseThrow(() -> new NotFoundException("Could not find your meeting requests, your email: [%s]".formatted(email)));
    }


    public void executeActionForMeetingRequest(Integer meetingRequestId) {
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingRequestId)
                .orElseThrow(() -> new ProcessingException("We can't find this meeting request"));
        MeetingRequestEntity meetingRequestEntityToSave = meetingRequestEntity.withCompletedDateTime(LocalDateTime.now());
        meetingRequestJpaRepository.saveAndFlush(meetingRequestEntityToSave);
    }

    public List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email) {
        return meetingRequestDAO.findAllCompletedServiceRequestsByEmail(email);
    }
}