package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(DoctorApiController.class)
public class DoctorApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private  DoctorService doctorService;
    @MockBean
    private  DoctorMapper doctorMapper;
    @MockBean
    private  AvailabilityScheduleService availabilityScheduleService;
    @MockBean
    private  DoctorDAO doctorDAO;

    @Test
    public void testGetAllDoctors() throws Exception {
        // Przygotowanie danych testowych
        List<DoctorDTO> doctors = new ArrayList<>();
        doctors.add(new DoctorDTO());
        doctors.add(new DoctorDTO());

//        DoctorDTOs doctorDTOs = new DoctorDTOs();
//        doctorDTOs.setDoctors(doctors);
//
//        // Mockowanie zachowania serwisu i DAO
//        Mockito.when(doctorDAO.findAllDoctors()).thenReturn(doctorDTOs);
//
//        // Wykonanie żądania GET na endpoint /api/doctors
//        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // Sprawdzenie oczekiwanego statusu i treści odpowiedzi
//        result.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(doctorDTOs)));
    }

    @Test
    public void testGetDoctorDetails() throws Exception {
        // Przygotowanie danych testowych
//        Doctor doctor = new Doctor();
//        doctor.setId(1);
//        doctor.setName("Dr. John");
//
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setId(1);
//        doctorDTO.setName("Dr. John");
//
//        // Mockowanie zachowania serwisu
//        Mockito.when(doctorService.findById(1)).thenReturn(Optional.of(doctor));
//
//        // Wykonanie żądania GET na endpoint /api/doctors/{doctorId}
//        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/1")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // Sprawdzenie oczekiwanego statusu i treści odpowiedzi
//        result.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(doctorDTO)));
    }

    // Możesz napisać podobne testy dla innych metod w kontrolerze
}
