package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
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
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private final AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;


    public List<MeetingRequest> availableServiceRequestsByDoctor() {
        return meetingRequestDAO.findAvailable();
    }  public List<MeetingRequest> availableEndedVisits() {
        return meetingRequestDAO.findEndedVisits();
    }


    @Transactional
    public void makeMeetingRequest(Patient patient, Doctor doctor, String description, AvailabilitySchedule visitTime) {
        validate(patient.getEmail());
        MeetingRequest meetingServiceRequest = buildMeetingRequest(patient,doctor, description, visitTime);
        AvailabilityScheduleEntity visitTimeEntity = availabilityScheduleEntityMapper.map(visitTime);
        availabilityScheduleJpaRepository.saveAndFlush(visitTimeEntity);
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
            String description,
            AvailabilitySchedule visitTime
    ) {
        OffsetDateTime now = OffsetDateTime.now();
        return MeetingRequest.builder()
                .meetingRequestNumber(generateNumber(now))
                .receivedDateTime(OffsetDateTime.now())
                .visitStart(visitTime.getSince())
                .visitEnd(visitTime.getToWhen())
                .description(description)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

     String generateNumber(OffsetDateTime when) {
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




    public void executeActionForMeetingRequest(Integer meetingRequestId) {
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingRequestId)
                .orElseThrow(() -> new ProcessingException("We can't find this meeting request"));
          meetingRequestEntity.setCompletedDateTime(OffsetDateTime.now());

        meetingRequestJpaRepository.save(meetingRequestEntity);
    }

    public List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email) {
        return meetingRequestDAO.findAllCompletedServiceRequestsByEmail(email);
    }

    public List<MeetingRequest> availableServiceRequestsByDoctor(String email) {
        return meetingRequestDAO.availableServiceRequests(email);
    }

    public List<MeetingRequest> availableEndedVisitsByDoctor(String email) {
        return meetingRequestDAO.availableEndedVisitsByDoctor(email);
    }
    public List<AvailabilitySchedule> generateTimeSlots(OffsetDateTime since, OffsetDateTime toWhen, Doctor doctor) {
        List<AvailabilitySchedule> timeSlots = new ArrayList<>();
        since = since.withMinute((since.getMinute() / 5) * 5);
        OffsetDateTime currentSlot = since;


        while (currentSlot.isBefore(toWhen)) {
            AvailabilitySchedule slot = new AvailabilitySchedule(0, currentSlot, currentSlot.plusMinutes(15), false, true, doctor);
            if(!existMeetingRequestWithThisSlot(currentSlot, currentSlot.plusMinutes(15), doctor)){
                timeSlots.add(slot);
            }
            currentSlot = currentSlot.plusMinutes(15);
        }
        for (int i = 1; i < timeSlots.size(); i++) {
            timeSlots.get(i).setAvailability_schedule_id(i);
        }
        return timeSlots;
    }

    private boolean existMeetingRequestWithThisSlot(OffsetDateTime since, OffsetDateTime toWhen, Doctor doctor) {
        return meetingRequestDAO.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctor);
    }
}