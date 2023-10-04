//package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.DoctorsControllerTestSupport;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.MeetingRequestRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
//import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
//import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
//import com.HealthMeetProject.code.util.DoctorDTOFixtures;
//import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//
//
//public class MeetingRequestControllerRest extends RestAssuredIntegrationTestBase
//        implements MeetingRequestControllerTestSupport, DoctorsControllerTestSupport {
//    @Autowired
//    private  MeetingRequestRepository meetingRequestRepository;
//    @Autowired
//    private  AvailabilityScheduleDAO availabilityScheduleDAO;
//    @Autowired
//    private  DoctorRepository doctorRepository;
//    @Autowired
//    private  DoctorEntityMapper doctorEntityMapper;
//    @Autowired
//    private  RoleRepository roleRepository;
//    @Autowired
//    private DoctorMapper doctorMapper;
//



//    @Test
//    void thatMeetingRequestIsAddedAndGivenProperly() {
//        //given
//        DoctorDTO doctorToSave = DoctorDTOFixtures.getDoctorDTO1();
//        RoleEntity roleEntity = roleRepository.findByRole("DOCTOR");
//        doctorToSave.getUser().setRoles(Set.of(roleEntity));
//        //when
//        saveDoctor(doctorToSave);
//
//        DoctorDTOs doctorDTO = doctorRepository.findAllDoctors();
//        Doctor doctor = doctorMapper.mapFromDTO(doctorDTO.getDoctorDTOList().get(0));
//        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
//        LocalDateTime since = LocalDateTime.of(2022, 3, 25, 8, 0);
//        LocalDateTime toWhen = LocalDateTime.of(2022, 3, 25, 16, 0);
//        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
//        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
//        meetingRequest.setVisitStart(since);
//        meetingRequest.setVisitEnd(toWhen);
//
//        addMeetingRequest(availabilitySchedule.getAvailability_schedule_id(), meetingRequest.getDescription(), doctorToSave.getEmail());
//        //when
//        List<MeetingRequest> all = meetingRequestRepository.findAvailable();
//        Assertions.assertEquals(all.size(), 1);
//
//    }
//}
