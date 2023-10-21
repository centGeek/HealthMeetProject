package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingRequestControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.PatientControllerTestSupport;
import com.HealthMeetProject.code.api.dto.*;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class MeetingRequestControllerTestRestAssuredIT extends RestAssuredIntegrationTestBase
        implements MeetingRequestControllerTestSupport, DoctorsControllerTestSupport, PatientControllerTestSupport {

    private AvailabilityScheduleDAO availabilityScheduleDAO;
    private DoctorJpaRepository doctorJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private DoctorEntityMapper doctorEntityMapper;
    private RoleRepository roleRepository;
    private DoctorMapper doctorMapper;
    private UserRepository userRepository;
    private MeetingRequestService meetingRequestService;


    @Test
    void thatMeetingRequestIsAddedAndGivenProperly() {
        //given
        patientJpaRepository.deleteAll();
        doctorJpaRepository.deleteAll();
        userRepository.deleteAll();
        DoctorDTO doctorToSave = DoctorDTOFixtures.getDoctorDTO1();
        doctorToSave.setUser(DoctorDTOFixtures.getUserDTO1());
        roleRepository.saveAndFlush(RoleEntity.builder().id(41322).role("DOCTOR").build());
        roleRepository.saveAndFlush(RoleEntity.builder().id(42231).role("PATIENT").build());
        List<RoleEntity> roleEntity = roleRepository.findAll();
        RoleEntity finalEntity = null;
        for (RoleEntity entity : roleEntity) {
            if (entity.getRole().equals("PATIENT")) {
                finalEntity = entity;
            }
        }
        RoleEntity doctorRoleEntity = roleRepository.findByRole("DOCTOR");
        doctorToSave.getUser().setRoles(Set.of(doctorRoleEntity));
        String description = "your friend in need is a friend indeed";
        PatientDTO patientDTO = PatientDTOFixtures.patientDTOExample1();
        assert finalEntity != null;
        patientDTO.setUser(UserData.builder().userName("j_kowalski").email("j.kowalski@tlen.com")
                .password("test").roles(Set.of(finalEntity)).active(true).build());

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
        availabilityScheduleDAO.deleteById(1);

    }
}
