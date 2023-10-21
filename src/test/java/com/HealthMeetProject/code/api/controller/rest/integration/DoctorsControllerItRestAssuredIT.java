package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@AllArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class DoctorsControllerItRestAssuredIT extends RestAssuredIntegrationTestBase implements DoctorsControllerTestSupport {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private PatientJpaRepository patientJpaRepository;

    @Test
    void thatDoctorsCanSaveAndListedCorrectly() {
        // given
        doctorJpaRepository.deleteAll();
        patientJpaRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.saveAndFlush(RoleEntity.builder().id(40).role("DOCTOR").build());
        DoctorDTO doctor1 = DoctorDTOFixtures.getDoctorDTO1();
        DoctorDTO doctor2 = DoctorDTOFixtures.getDoctorDTO2();
        // when
        RoleEntity roleEntity = roleRepository.findByRole("DOCTOR");
        roleRepository.saveAndFlush(roleEntity);
        doctor1.getUser().setRoles(Set.of(roleEntity));
        doctor2.getUser().setRoles(Set.of(roleEntity));
        saveDoctor(doctor1);
        saveDoctor(doctor2);
        DoctorDTOs retrievedEmployees = listDoctors();
        DoctorDTO doctor3 = getDoctor(doctor1.getEmail());
        // then
        assertThat(retrievedEmployees.getDoctorDTOList().get(0).getEmail()).isEqualTo(doctor1.getEmail());
        assertThat(retrievedEmployees.getDoctorDTOList().get(0).getUser().getUserName()).isEqualTo(doctor1.getUser().getUserName());
        assertThat(doctor3).isEqualTo(listDoctors().getDoctorDTOList().get(0));
    }
}
