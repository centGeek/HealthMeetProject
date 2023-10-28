package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.DoctorApiController;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.api.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.api.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorApiControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorMapper doctorMapper;

    @Mock
    private AvailabilityScheduleService availabilityScheduleService;

    @Mock
    private DoctorDAO doctorDAO;

    @InjectMocks
    private DoctorApiController controller;


    @Test
    public void testGetAllDoctors() {
        //given
        List<DoctorDTO> doctors = new ArrayList<>();
        doctors.add(DoctorDTOFixtures.getDoctorDTO1());
        when(doctorDAO.findAllDoctors()).thenReturn(DoctorDTOs.of(doctors));

        //when
        DoctorDTOs result = controller.getAllDoctors();

        //then
        assertNotNull(result);
        assertEquals(doctors, result.getDoctorDTOList());
    }

    @Test
    public void testGetDoctorDetails() {
        //given
        Integer doctorId = 1;
        DoctorDTO doctorDTO = DoctorDTOFixtures.getDoctorDTO1();
        when(doctorService.findById(doctorId)).thenReturn(Optional.of(DoctorExampleFixtures.doctorExample1()));
        when(doctorMapper.mapToDTO(any(Doctor.class))).thenReturn(doctorDTO);

        //when
        DoctorDTO result = controller.getDoctorDetails(doctorId);

        //then
        assertNotNull(result);
        assertEquals(doctorDTO, result);
    }

    @Test
    public void testGetDoctorDetailsNotFound() {
        //given
        Integer doctorId = 1;
        when(doctorService.findById(doctorId)).thenReturn(Optional.empty());

        //when & Assert
        assertThrows(NotFoundException.class, () -> controller.getDoctorDetails(doctorId));
    }

    @Test
    public void testGetDoctorAvailableTerms() {
        //given
        Integer doctorId = 1;
        List<AvailabilityScheduleDTO> availableTerms = new ArrayList<>();
        availableTerms.add(DoctorExampleFixtures.availabilityScheduleDTO1());
        when(doctorService.findById(doctorId)).thenReturn(Optional.of(DoctorExampleFixtures.doctorExample1()));
        when(availabilityScheduleService.findAllAvailableTermsByGivenDoctor(anyString())).thenReturn(availableTerms);
        when(doctorMapper.mapToDTO(any())).thenReturn(DoctorDTOFixtures.getDoctorDTO1());
        //when
        AvailabilityScheduleDTOs result = controller.getDoctorAvailableTerms(doctorId);

        //then
        assertNotNull(result);
        assertEquals(availableTerms, result.getAvailabilityScheduleDTOList());
    }

    @Test
    public void testUpdateDoctor() {
        //given
        Integer doctorId = 1;
        DoctorDTO updatedDoctorDTO = DoctorExampleFixtures.doctorDTOExample3();
        Doctor existingDoctor = DoctorExampleFixtures.doctorExample1();
        when(doctorService.findById(doctorId)).thenReturn(Optional.of(existingDoctor));

        //when
        ResponseEntity<?> responseEntity = controller.updateDoctor(doctorId, updatedDoctorDTO);

        //then
        assertNotNull(responseEntity);
        //noinspection deprecation
        assertEquals(200, responseEntity.getStatusCodeValue()); // HTTP 200 OK
    }

    @Test
    public void testUpdateDoctorNotFound() {
        //given
        Integer doctorId = 1;
        DoctorDTO updatedDoctorDTO = DoctorExampleFixtures.doctorDTOExample3();
        when(doctorService.findById(doctorId)).thenReturn(Optional.empty());

        //when
        ResponseEntity<?> responseEntity = controller.updateDoctor(doctorId, updatedDoctorDTO);

        //then
        assertNotNull(responseEntity);
        //noinspection deprecation
        assertEquals(204, responseEntity.getStatusCodeValue()); // HTTP 204 No Content
    }
}
