package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityRestApiMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class MeetingRequestService {
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    private final DoctorMapper doctorMapper;
    private final PatientService patientService;
    private final MeetingRequestDAO meetingRequestDAO;
    private final MeetingRequestJpaRepository meetingRequestJpaRepository;
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private final AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    private final MeetingRequestEntityMapper meetingRequestEntityMapper;
    private final AvailabilityScheduleDAO availabilityScheduleDAO;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final MeetingRequestEntityRestApiMapper meetingRequestEntityRestApiMapper;
    private final DoctorRepository doctorRepository;



    public boolean canCancelMeeting(LocalDateTime visitStart){
        return LocalDateTime.now().plusMinutes(30).plusSeconds(1).isBefore(visitStart);
    }
    @Transactional
    public MeetingRequest makeMeetingRequest(Patient patient, DoctorDTO doctorDTO, String description, AvailabilityScheduleDTO visitTimeDTO) {
        validate(patient.getEmail());
        Doctor doctor = doctorMapper.mapFromDTO(doctorDTO);
        AvailabilitySchedule visitTime = availabilityScheduleMapper.mapFromDTO(visitTimeDTO);
        MeetingRequest meetingServiceRequest = buildMeetingRequest(patient,doctor, description, visitTime);
        AvailabilityScheduleEntity visitTimeEntity = availabilityScheduleEntityMapper.mapToEntity(visitTime);
        availabilityScheduleJpaRepository.saveAndFlush(visitTimeEntity);
        patientService.saveMeetingRequest(meetingServiceRequest, patient);
        return meetingServiceRequest;
    }


    void validate(String email) {
        List<MeetingRequest> meetingRequests = meetingRequestDAO.findAllActiveMeetingRequests(email);
        if (!meetingRequests.isEmpty()) {
            for (MeetingRequest meetingRequest : meetingRequests) {
                if(meetingRequest.getVisitStart().isAfter(LocalDateTime.now())){
                    throw new ProcessingException(
                            "There should be only one active meeting request at a time, patient email: [%s]".formatted(email));
                }
            }
        }
    }


    public MeetingRequest buildMeetingRequest(
            Patient patient,
            Doctor doctor,
            String description,
            AvailabilitySchedule visitTime
    ) {
        LocalDateTime now = LocalDateTime.now();
             return MeetingRequest.builder()
                .meetingRequestNumber(generateNumber(now))
                .receivedDateTime(LocalDateTime.now())
                .visitStart(visitTime.getSince())
                .visitEnd(visitTime.getToWhen())
                .description(description)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

     String generateNumber(LocalDateTime when) {
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

    public List<MeetingRequest> restFindByDoctorEmail(String email){
        return meetingRequestJpaRepository.findAllByDoctorEmail(email)
                .stream().map(meetingRequestEntityRestApiMapper::mapFromEntity).toList();
    }


    public MeetingRequestEntity executeActionForMeetingRequest(Integer meetingRequestId) {
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingRequestId)
                .orElseThrow(() -> new ProcessingException("We can't find this meeting request"));
        meetingRequestEntity.setCompletedDateTime(LocalDateTime.now());
        meetingRequestJpaRepository.save(meetingRequestEntity);
        return meetingRequestEntity;
    }

    public List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email) {
        return meetingRequestDAO.findAllCompletedServiceRequestsByEmail(email);
    }

    public List<MeetingRequest> availableServiceRequestsByDoctor(String email) {
        return meetingRequestDAO.findAllActiveMeetingRequestsByDoctor(email);
    }public List<MeetingRequest> restAvailableServiceRequestsByDoctor(String email) {
        return meetingRequestDAO.restFindAllActiveMeetingRequestsByDoctor(email);

    }

    public List<MeetingRequest> availableEndedVisitsByDoctor(String email) {
        return meetingRequestDAO.completedMeetingRequestsByDoctor(email);
    }
    public List<AvailabilitySchedule> generateTimeSlots(LocalDateTime since, LocalDateTime toWhen, Doctor doctor) {
        List<AvailabilitySchedule> timeSlots = new ArrayList<>();
        since = since.withMinute((since.getMinute() / 5) * 5);
        LocalDateTime currentSlot = since;


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

    private boolean existMeetingRequestWithThisSlot(LocalDateTime since, LocalDateTime toWhen, Doctor doctor) {
        return meetingRequestDAO.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctor);
    }

    public MeetingRequest findById(Integer meetingId) {
        return meetingRequestEntityMapper.mapFromEntity(
                meetingRequestJpaRepository.findById(meetingId).orElseThrow(() ->
                        new NotFoundException("Not found meeting request by given id")));
    }
    public MeetingRequest restFindById(Integer meetingId) {
        return meetingRequestEntityRestApiMapper.mapFromEntity(
                meetingRequestJpaRepository.findById(meetingId).orElseThrow(() ->
                        new NotFoundException("Not found meeting request by given id")));
    }
    public AvailabilityScheduleDTO getAvailabilitySchedule(Integer availability_schedule_id, Integer selectedSlotId) {
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.findById(availability_schedule_id);
        Doctor doctor = availabilitySchedule.getDoctor();
        List<AvailabilitySchedule> particularVisitTime = generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), doctor);
        List<AvailabilityScheduleDTO> particularVisitTimeDTO = particularVisitTime.stream().map(availabilityScheduleMapper::mapToDTO).toList();
        return particularVisitTimeDTO.get(selectedSlotId);
    }
    public AvailabilityScheduleDTO restGetAvailabilitySchedule(Integer availability_schedule_id, Integer selectedSlotId, String doctorEmail) {
        List<AvailabilitySchedule> availabilitySchedules = availabilityScheduleDAO.restFindAllAvailableTermsByGivenDoctor(doctorEmail);
        AvailabilitySchedule availabilitySchedule = new AvailabilitySchedule();
        for (AvailabilitySchedule schedule : availabilitySchedules) {
            if(schedule.getAvailability_schedule_id()==availability_schedule_id){
                availabilitySchedule = schedule;
            }
        }
        Doctor doctor = doctorRepository.findByEmail(doctorEmail).orElseThrow();
        List<AvailabilitySchedule> particularVisitTime = generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), doctor);
        List<AvailabilityScheduleDTO> particularVisitTimeDTO = particularVisitTime.stream().map(availabilityScheduleMapper::mapToDTO).toList();
        return particularVisitTimeDTO.get(selectedSlotId);
    }
    public List<Boolean> canCancelMeetingList(List<MeetingRequest> allUpcomingVisits) {
        List<Boolean> canCancelMeetingList = new ArrayList<>();
        for (MeetingRequest allUpcomingVisit : allUpcomingVisits) {
            boolean canCancelMeeting = canCancelMeeting(allUpcomingVisit.getVisitStart());
            canCancelMeetingList.add(canCancelMeeting);
        }
        return canCancelMeetingList;
    }
    public List<AvailabilityScheduleDTO> getParticularVisitTimeDTO(Integer availabilityScheduleId) {
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.findById(availabilityScheduleId);
        Doctor doctor = availabilitySchedule.getDoctor();
        List<AvailabilitySchedule> particularVisitTime = generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), doctor);
        List<AvailabilityScheduleDTO> particularVisitTimeDTO = particularVisitTime.stream().map(availabilityScheduleMapper::mapToDTO).toList();

        if (particularVisitTime.isEmpty()) {
            availabilitySchedule.setAvailableDay(false);
            availabilityScheduleService.save(availabilitySchedule);
        }
        return particularVisitTimeDTO;
    }

}