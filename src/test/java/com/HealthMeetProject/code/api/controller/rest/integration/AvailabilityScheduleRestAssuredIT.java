package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.AvailabilityScheduleControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.api.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.api.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

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
        AvailabilityScheduleDTO availabilityScheduleDTO = allDoctorAvailableTerms.getAvailabilityScheduleDTOList()
                .get(0);
        Assertions.assertThat(availabilityScheduleDTO.getSince()).isEqualTo(availabilitySchedule.getSince());

        availabilityScheduleDTO.setAvailableDay(true);
        addTerm(availabilityScheduleDTO);

        AvailabilityScheduleDTOs allAvailableWorkingDays = getAllAvailableWorkingDays();
        Assertions.assertThat(allAvailableWorkingDays.getAvailabilityScheduleDTOList()
                .size()).isEqualTo(2);

        deleteTerm(allAvailableWorkingDays.getAvailabilityScheduleDTOList().get(0).getAvailability_schedule_id());
        AvailabilityScheduleDTOs allAvailableWorkingDaysAfterDeleting = getAllAvailableWorkingDays();
        Assertions.assertThat(allAvailableWorkingDaysAfterDeleting.getAvailabilityScheduleDTOList()
                .size()).isEqualTo(1);
    }
}
