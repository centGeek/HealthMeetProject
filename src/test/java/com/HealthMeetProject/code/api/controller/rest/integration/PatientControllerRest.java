package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.PatientControllerTestSupport;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
public class PatientControllerRest  extends RestAssuredIntegrationTestBase implements PatientControllerTestSupport {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PatientJpaRepository patientJpaRepository;
    @Test
    void thatPatientFindingByEmailAndSavingWorksCorrectly() {
        // given
        patientJpaRepository.deleteAll();
        PatientDTO patient1 = PatientDTOFixtures.patientDTOExample1();
        RoleEntity roleEntity = roleRepository.findByRole("PATIENT");
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
