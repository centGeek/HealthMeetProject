package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.AvailabilityScheduleApiController;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AvailabilityScheduleApiMockitoControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private AvailabilityScheduleService availabilityScheduleService;

    @Mock
    private AvailabilityScheduleDAO availabilityScheduleDAO;

    @Mock
    private DoctorEntityMapper doctorEntityMapper;

    @InjectMocks
    private AvailabilityScheduleApiController controller;

    @Test
    public void testGetAllAvailableTerms() {
        //given
        List<AvailabilityScheduleDTO> availableTerms = new ArrayList<>();
        when(availabilityScheduleDAO.findAll()).thenReturn(availableTerms);

        //when
        AvailabilityScheduleDTOs result = controller.getAllAvailableWorkingDays();

        //then
        assertNotNull(result);
        assertEquals(availableTerms, result.getAvailabilityScheduleDTOList());
    }

    @Test
    public void testGetDoctorAvailableTerms() {
        //given
        Integer doctorId = 1;
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        List<AvailabilityScheduleDTO> availableTerms = new ArrayList<>();
        //when
        when(doctorService.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctor.getEmail())).thenReturn(availableTerms);

        AvailabilityScheduleDTOs result = controller.getDoctorAvailableTerms(doctorId);

        //then
        assertNotNull(result);
        assertEquals(availableTerms, result.getAvailabilityScheduleDTOList());
    }

    @Test
    public void testAddTerms() {
        //given
        Integer doctorId = 1;
        AvailabilityScheduleDTO availabilityScheduleDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
        availabilityScheduleDTO.setSince(LocalDateTime.now());

        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        doctor.setDoctorId(doctorId);
        //when
        when(doctorService.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorEntityMapper.mapToEntity(doctor)).thenReturn(DoctorExampleFixtures.doctorEntityExample1());

        ResponseEntity<AvailabilityScheduleDTO> responseEntity = controller.addTerms(doctorId, availabilityScheduleDTO);

        //then
        assertNotNull(responseEntity);
        //noinspection deprecation
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteTerm() {
        //given
        Integer availabilityScheduleId = 1;
        //when
        doNothing().when(availabilityScheduleDAO).deleteById(availabilityScheduleId);

        ResponseEntity<?> responseEntity = controller.deleteTerm(availabilityScheduleId);

        //then
        assertNotNull(responseEntity);
        //noinspection deprecation
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteTermNotFound() {
        //given
        Integer availabilityScheduleId = 1;
        //when
        doThrow(new IllegalArgumentException()).when(availabilityScheduleDAO).deleteById(availabilityScheduleId);
        ResponseEntity<?> responseEntity = controller.deleteTerm(availabilityScheduleId);

        //then
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue()); // HTTP 404 Not Found
    }
}
