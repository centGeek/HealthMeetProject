package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DoctorsControllerItRestAssuredIT extends RestAssuredIntegrationTestBase implements DoctorsControllerTestSupport {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void thatDoctorsCanSaveAndListedCorrectly() {
        // given
        DoctorDTO doctor1 = DoctorDTOFixtures.getDoctorDTO1();
        DoctorDTO doctor2 = DoctorDTOFixtures.getDoctorDTO2();
        // when
        roleRepository.saveAndFlush(RoleEntity.builder().role("DOCTOR").id(30000).build());
        saveDoctor(doctor1);
        saveDoctor(doctor2);
        DoctorDTOs retrievedEmployees = listDoctors();

        // then
        assertThat(retrievedEmployees.getDoctorDTOList().get(0).getEmail()).isEqualTo(doctor1.getEmail());
        assertThat(retrievedEmployees.getDoctorDTOList().get(0).getUser().getUserName()).isEqualTo(doctor1.getUser().getUserName());
    }
}
