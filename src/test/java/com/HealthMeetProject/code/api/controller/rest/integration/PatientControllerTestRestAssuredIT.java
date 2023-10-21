package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.PatientControllerTestSupport;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.infrastructure.database.repository.MeetingRequestRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerTestRestAssuredIT extends RestAssuredIntegrationTestBase implements PatientControllerTestSupport {
    private RoleRepository roleRepository;

    private DoctorJpaRepository doctorJpaRepository;
    private UserRepository userRepository;
    private PatientJpaRepository patientJpaRepository;
    @Test
    void thatPatientFindingByEmailAndSavingWorksCorrectly() {
        // given
        doctorJpaRepository.deleteAll();
        patientJpaRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.saveAndFlush(RoleEntity.builder().role("PATIENT").build());
        RoleEntity roleEntity = roleRepository.findByRole("PATIENT");
        PatientDTO patient1 = PatientDTOFixtures.patientDTOExample1();
        patient1.setUser(UserData.builder().userName("j_kowalski").email("j.kowalski@tlen.com")
                .password("test").roles(Set.of(roleEntity)).active(true).build());
        PatientDTO patient2 = PatientDTOFixtures.patientDTOExample2();
        patient2.setUser(UserData.builder().userName("k_zdunski").email("k.zdunski@tlen.com")
                .password("test").roles(Set.of(roleEntity)).active(true).build());
        // when
        savePatient(patient1);
        savePatient(patient2);
        PatientDTO byEmail1 = findByEmail(patient1.getEmail());
        PatientDTO byEmail2 = findByEmail(patient2.getEmail());
        // then
        Assertions.assertEquals(byEmail1.getEmail(), patient1.getEmail());
        Assertions.assertEquals(byEmail1.getUser().getUserName(), patient1.getUser().getUserName());
        Assertions.assertEquals(byEmail2.getUser().getEmail(), patient2.getUser().getEmail());
    }
}
