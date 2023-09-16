package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientRepositoryTest {

    @InjectMocks
    private PatientRepository patientRepository;

    @Mock
    private PatientJpaRepository patientJpaRepository;

    @Mock
    private MeetingRequestJpaRepository meetingRequestJpaRepository;

    @Mock
    private PatientEntityMapper patientEntityMapper;

    @Mock
    private MeetingRequestEntityMapper meetingRequestEntityMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;


    @Test
    void testIssueInvoice() {
        // given
        Patient patient = PatientExampleFixtures.patientExample1();
        PatientEntity patientEntity = PatientExampleFixtures.patientEntityExample1();
        when(patientEntityMapper.mapToEntity(patient)).thenReturn(patientEntity);

        // when
        patientRepository.issueInvoice(patient);

        // then
        verify(patientJpaRepository).saveAndFlush(patientEntity);
    }

    @Test
    void testSaveMeetingRequest() {
        // given
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        Patient patient = PatientExampleFixtures.patientExample1();
        MeetingRequestEntity meetingRequestEntity = MeetingRequestsExampleFixtures.meetingRequestDataEntityExample1();
        PatientEntity patientEntity = PatientExampleFixtures.patientEntityExample1();
        when(meetingRequestEntityMapper.mapToEntity(meetingRequest)).thenReturn(meetingRequestEntity);
        when(patientEntityMapper.mapToEntity(patient)).thenReturn(patientEntity);

        // when
        patientRepository.saveMeetingRequest(meetingRequest, patient);

        // then
        verify(meetingRequestJpaRepository).saveAndFlush(meetingRequestEntity);
    }
    @Test
    void testSavePatient() {
        //given
        Patient patient = PatientExampleFixtures.patientExample1();
        patient.setUser(PatientDTOFixtures.userDataPatient());
        PatientEntity patientEntity = PatientExampleFixtures.patientEntityExample1();
        patientEntity.setUser(PatientDTOFixtures.userDataPatientEntity());
        when(patientEntityMapper.mapToEntity(patient)).thenReturn(patientEntity);
        when(patientJpaRepository.save(patientEntity)).thenReturn(patientEntity);

        //when
        patientRepository.savePatient(patient);

        //then
        verify(patientJpaRepository).save(patientEntity);
    }

    @Test
    void testFindById() {
        //given
        int patientId = 1;
        //when
        when(patientJpaRepository.findById(patientId)).thenReturn(Optional.of(PatientExampleFixtures.patientEntityExample1()));
        when(patientEntityMapper.mapFromEntity(any())).thenReturn(PatientExampleFixtures.patientExample1());

        Patient foundPatient = patientRepository.findById(patientId);

        //ten
        assertNotNull(foundPatient);
    }

    @Test
    void testRegister() {
        // given
        PatientDTO patientDTO = PatientDTOFixtures.patientDTOExample1();
        patientDTO.setUser(PatientDTOFixtures.userDataPatient());
        UserEntity userEntity = PatientDTOFixtures.userDataPatientEntity();
        PatientEntity patientEntity = PatientExampleFixtures.patientEntityExample1();
        // when
        when(userRepository.saveAndFlush(any())).thenReturn(userEntity);
        when(patientJpaRepository.saveAndFlush(any())).thenReturn(patientEntity);
        when(roleRepository.findByRole("PATIENT")).thenReturn(new RoleEntity(2, "PATIENT"));

        patientRepository.register(patientDTO);

        // then
        verify(userRepository).saveAndFlush(any());
        verify(patientJpaRepository).saveAndFlush(any());
    }

    @Test
    void testFindByEmail() {
        // given
        String email = "test@example.com";
        PatientEntity patientEntity = new PatientEntity();
        when(patientJpaRepository.findByEmail(email)).thenReturn(java.util.Optional.of(patientEntity));
        when(patientEntityMapper.mapFromEntity(patientEntity)).thenReturn(new Patient());

        // when
        Patient foundPatient = patientRepository.findByEmail(email);

        // then
        assertNotNull(foundPatient);
    }
}
