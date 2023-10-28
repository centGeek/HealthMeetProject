package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.MeetingProcessingApiController;
import com.HealthMeetProject.code.api.controller.rest.MeetingRequestApiController;
import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingProcessingControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingRequestControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.PatientControllerTestSupport;
import com.HealthMeetProject.code.api.dto.*;
import com.HealthMeetProject.code.api.dto.api.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.api.FinalizeSlotDTO;
import com.HealthMeetProject.code.api.dto.api.MeetingRequestsDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class MeetingRequestControllerTestRestAssuredIT extends RestAssuredIntegrationTestBase
        implements MeetingRequestControllerTestSupport, DoctorsControllerTestSupport, PatientControllerTestSupport
        , MeetingProcessingControllerTestSupport {

    private AvailabilityScheduleDAO availabilityScheduleDAO;
    private DoctorJpaRepository doctorJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private DoctorEntityMapper doctorEntityMapper;
    private RoleRepository roleRepository;
    private DoctorMapper doctorMapper;
    private UserRepository userRepository;
    private MeetingRequestService meetingRequestService;
    private MeetingRequestApiController meetingRequestApiController;
    private MeetingProcessingApiController meetingProcessingApiController;


    @Test
    void thatMeetingRequestIsAddedAndGivenProperly() {
        //given
        patientJpaRepository.deleteAll();
        doctorJpaRepository.deleteAll();
        userRepository.deleteAll();

        DoctorDTO doctorToSave = DoctorDTOFixtures.getDoctorDTO1();
        doctorToSave.setUser(DoctorDTOFixtures.getUserDTO1());
        roleRepository.saveAndFlush(RoleEntity.builder().role("DOCTOR").build());
        roleRepository.saveAndFlush(RoleEntity.builder().role("PATIENT").build());
        RoleEntity doctorRoleEntity = roleRepository.findByRole("DOCTOR");
        doctorToSave.getUser().setRoles(Set.of(doctorRoleEntity));
        String description = "your friend in need is a friend indeed";
        PatientDTO patientDTO = PatientDTOFixtures.patientDTOExample1();
        patientDTO.setUser(UserData.builder().userName("j_kowalski").email("j.kowalski@tlen.com")
                .password("test").roles(Set.of(doctorRoleEntity)).active(true).build());

        //when
        saveDoctor(doctorToSave);
        savePatient(patientDTO);
        DoctorDTO doctorDTO = getDoctor(doctorToSave.getEmail());
        Doctor doctor = doctorMapper.mapFromDTO(doctorDTO);
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        LocalDateTime since = LocalDateTime.of(2026, 3, 25, 8, 0);
        LocalDateTime toWhen = LocalDateTime.of(2026, 3, 25, 16, 0);
        availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);

        MeetingRequestDTO meetingRequestDTO = MeetingRequestDTO.builder().availabilityScheduleId(1)
                .patientEmail(patientDTO.getEmail())
                .description(description).build();
        addMeetingRequest(meetingRequestDTO);
        MeetingRequestsDTOs meetingRequests = getMeetingRequests(doctor.getEmail());
        Assertions.assertEquals(meetingRequests.getMeetingRequestList().size(), 1);
        deleteMeeting(meetingRequests.getMeetingRequestList().get(0).getMeetingId());
        List<MeetingRequest> meetingRequestsAfterDeleting = meetingRequestService.restFindByDoctorEmail(doctor.getEmail());


        Assertions.assertEquals(meetingRequestsAfterDeleting.size(), 0);

        List<AvailabilitySchedule> byId = availabilityScheduleDAO.restFindAllAvailableTermsByGivenDoctor(doctor.getEmail());
        int availabilityScheduleId = byId.get(0).getAvailability_schedule_id();
        AvailabilityScheduleDTOs slotsByMeetingRequest = getSlotsByMeetingRequest(availabilityScheduleId);
        Assertions.assertEquals(slotsByMeetingRequest.getAvailabilityScheduleDTOList().size(), 32);
        addMeetingRequest(meetingRequestDTO);

        FinalizeSlotDTO finalizeSlotDTO = FinalizeSlotDTO.builder().availabilityScheduleId(availabilityScheduleId)
                .doctorEmail(doctor.getEmail()).selectedSlotId(2).build();

        finalizeMeetingRequest(finalizeSlotDTO);

        List<AvailabilityScheduleDTO> availabilityScheduleDTOS = availabilityScheduleDAO.restFindAll();
        Assertions.assertEquals(availabilityScheduleDTOS.size(), 2);
        Assertions.assertFalse(availabilityScheduleDTOS.get(1).isAvailableTerm());
        Assertions.assertFalse(availabilityScheduleDTOS.get(1).isAvailableDay());


        MeetingRequestsDTOs waitingForConfirmationMeetingRequests = getWaitingForConfirmationMeetingRequests(doctorDTO.getDoctorId());
        Assertions.assertEquals(1, waitingForConfirmationMeetingRequests.getMeetingRequestList().size());
        Integer meetingId = waitingForConfirmationMeetingRequests.getMeetingRequestList().get(0).getMeetingId();

        confirmMeetingRequest(meetingId);

        MeetingRequestsDTOs waitingForConfirmationMeetingRequestsAfterConfirmation = getWaitingForConfirmationMeetingRequests(doctorDTO.getDoctorId());
        Assertions.assertEquals(0, waitingForConfirmationMeetingRequestsAfterConfirmation.getMeetingRequestList().size());


        MeetingRequestsDTOs endedVisitsByPatientEmail =findEndedVisitsByPatientEmail(doctorDTO.getEmail(), patientDTO.getEmail());
        Assertions.assertEquals(0, endedVisitsByPatientEmail.getMeetingRequestList().size());


    }
}
