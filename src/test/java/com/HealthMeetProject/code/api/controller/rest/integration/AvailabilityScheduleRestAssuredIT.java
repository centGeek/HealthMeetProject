//package com.HealthMeetProject.code.api.controller.rest.integration;
//
//import com.HealthMeetProject.code.api.controller.rest.integration.support.AvailabilityScheduleControllerTestSupport;
//import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
//import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
//import com.HealthMeetProject.code.api.dto.DoctorDTO;
//import com.HealthMeetProject.code.api.dto.DoctorDTOs;
//import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
//import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
//import com.HealthMeetProject.code.domain.AvailabilitySchedule;
//import com.HealthMeetProject.code.domain.Doctor;
//import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
//import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
//import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
//import com.HealthMeetProject.code.util.DoctorDTOFixtures;
//import com.HealthMeetProject.code.util.DoctorExampleFixtures;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Set;
//
//public class AvailabilityScheduleRestAssuredIT extends RestAssuredIntegrationTestBase
//        implements AvailabilityScheduleControllerTestSupport, DoctorsControllerTestSupport {
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    @Autowired
//    private AvailabilityScheduleMapper availabilityScheduleMapper;
//
//     @Autowired
//    private DoctorMapper doctorMapper;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    void thatTermIsAddedProperly() {
//        //given
//        DoctorDTO doctor = DoctorDTOFixtures.getDoctorDTO2();
//        // when
//        RoleEntity roleEntity = roleRepository.findByRole("DOCTOR");
//        roleRepository.saveAndFlush(roleEntity);
//        doctor.getUser().setRoles(Set.of(roleEntity));
//        saveDoctor(doctor);
//        AvailabilitySchedule availabilitySchedule = DoctorExampleFixtures.availabilitySchedule1();
//        //when
//        DoctorDTOs doctorsSaved = listDoctors();
//        Doctor doctorSaved = doctorMapper.mapFromDTO(doctorsSaved.getDoctorDTOList().get(0));
//        availabilitySchedule.setDoctor(doctorSaved);
//        addTerm(availabilityScheduleMapper.mapToDTO(availabilitySchedule));
//        AvailabilityScheduleDTOs allDoctorAvailableTerms = getAllDoctorAvailableTerms(doctorSaved.getDoctorId());
//        //then
//        Assertions.assertThat(allDoctorAvailableTerms.getAvailabilityScheduleDTOList()
//                .get(0).getSince()).isEqualTo(availabilitySchedule.getSince());
//
//    }
//}
