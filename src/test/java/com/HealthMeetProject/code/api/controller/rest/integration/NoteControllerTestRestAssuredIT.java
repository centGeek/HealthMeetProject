package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingRequestControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.NoteControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.PatientControllerTestSupport;
import com.HealthMeetProject.code.api.dto.*;
import com.HealthMeetProject.code.api.dto.api.IllnessHistoryDTOs;
import com.HealthMeetProject.code.api.dto.api.MeetingRequestsDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
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

public class NoteControllerTestRestAssuredIT extends RestAssuredIntegrationTestBase
        implements NoteControllerTestSupport, MeetingRequestControllerTestSupport, PatientControllerTestSupport, DoctorsControllerTestSupport {

    private DoctorMapper doctorMapper;
    private DoctorEntityMapper doctorEntityMapper;
    private AvailabilityScheduleDAO availabilityScheduleDAO;
    private PatientJpaRepository patientJpaRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private NoteJpaRepository noteJpaRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PatientEntityMapper patientEntityMapper;

    @Test
    public void thatNoteIsAddedAndGivenProperly() {
        //given,when
        doctorJpaRepository.deleteAll();
        patientJpaRepository.deleteAll();
        userRepository.deleteAll();

        roleRepository.save(RoleEntity.builder().id(323).role("DOCTOR").build());
        roleRepository.save(RoleEntity.builder().id(324).role("PATIENT").build());
        DoctorDTO doctorDTO = DoctorDTOFixtures.getDoctorDTO1();
        doctorDTO.setUser(DoctorDTOFixtures.userDataDoctor());
        doctorDTO.getUser().setRoles(Set.of(roleRepository.findByRole("DOCTOR")));
        saveDoctor(doctorDTO);
        DoctorDTO doctorDTOSaved = getDoctor(doctorDTO.getEmail());
        Doctor doctor = doctorMapper.mapFromDTO(doctorDTOSaved);
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        LocalDateTime since = LocalDateTime.of(2026, 3, 25, 8, 0);
        LocalDateTime toWhen = LocalDateTime.of(2026, 3, 25, 16, 0);
        availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
        List<AvailabilitySchedule> allAvailableTermsByGivenDoctor =
                availabilityScheduleDAO.restFindAllAvailableTermsByGivenDoctor(doctorEntity.getEmail());

        PatientDTO patientDTO = PatientDTOFixtures.patientDTOExample1();
        patientDTO.setUser(UserData.builder().userName("j_kowalski").email("j.kowalski@tlen.com")
                .password("test").roles(Set.of(roleRepository.findByRole("PATIENT"))).active(true).build());
        savePatient(patientDTO);
        MeetingRequestDTO meetingRequestDTO = MeetingRequestDTO.builder()
                .description("Koń sie pasie")
                .patientEmail(patientDTO.getEmail())
                .availabilityScheduleId(allAvailableTermsByGivenDoctor.get(0).getAvailability_schedule_id())
                .build();


        addMeetingRequest(meetingRequestDTO);
        MeetingRequestsDTOs meetingRequests = getMeetingRequests(doctorEntity.getEmail());
        Note noteToSave = NoteExampleFixtures.noteExample1();
        noteToSave.setDoctor(doctor);
        PatientEntity patientEntity = patientJpaRepository.findByEmail(patientDTO.getEmail()).orElseThrow();
        Patient patient = patientEntityMapper.mapFromEntity(patientEntity);
        noteToSave.setPatient(patient);
        Integer meetingId = meetingRequests.getMeetingRequestList().get(0).getMeetingId();
        addNote(meetingId, noteToSave);

        Note note = getNote(meetingId, "Cos z glowa");
        //then
        Assertions.assertEquals(note.getDescription(), meetingRequestDTO.getDescription());

        IllnessHistoryDTOs illnessHistory = getIllnessHistory(patient.getEmail());
        Assertions.assertEquals(illnessHistory.getIllnessList().get(0), note.getIllness());

        noteJpaRepository.deleteAll();
        availabilityScheduleDAO.deleteById(allAvailableTermsByGivenDoctor.get(0).getAvailability_schedule_id());
        deleteMeeting(meetingId);
        doctorJpaRepository.deleteAll();
        patientJpaRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

    }
}
