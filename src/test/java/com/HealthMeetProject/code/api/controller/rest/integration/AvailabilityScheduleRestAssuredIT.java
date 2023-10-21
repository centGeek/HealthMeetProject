package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.AvailabilityScheduleControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.Set;
@AllArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class AvailabilityScheduleRestAssuredIT extends RestAssuredIntegrationTestBase
        implements AvailabilityScheduleControllerTestSupport, DoctorsControllerTestSupport {

    private AvailabilityScheduleMapper availabilityScheduleMapper;
    private DoctorMapper doctorMapper;
    private RoleRepository roleRepository;
    private DoctorJpaRepository doctorRepository;
    private PatientJpaRepository patientRepository;
    private UserRepository userRepository;

    @Test
    void thatTermIsAddedProperly() {
        //given
        DoctorDTO doctor = DoctorDTOFixtures.getDoctorDTO2();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
        userRepository.deleteAll();
        // when
        roleRepository.saveAndFlush(RoleEntity.builder().id(212).role("DOCTOR").build());
        RoleEntity roleEntity = roleRepository.findByRole("DOCTOR");
        roleRepository.saveAndFlush(roleEntity);
        doctor.getUser().setRoles(Set.of(roleEntity));
        saveDoctor(doctor);
        AvailabilitySchedule availabilitySchedule = DoctorExampleFixtures.availabilitySchedule1();
        //when
        DoctorDTOs doctorsSaved = listDoctors();
        Doctor doctorSaved = doctorMapper.mapFromDTO(doctorsSaved.getDoctorDTOList().get(0));
        availabilitySchedule.setDoctor(doctorSaved);
        addTerm(availabilityScheduleMapper.mapToDTO(availabilitySchedule));
        AvailabilityScheduleDTOs allDoctorAvailableTerms = getAllDoctorAvailableTerms(doctorSaved.getDoctorId());
        //then
        Assertions.assertThat(allDoctorAvailableTerms.getAvailabilityScheduleDTOList()
                .get(0).getSince()).isEqualTo(availabilitySchedule.getSince());

    }
}
