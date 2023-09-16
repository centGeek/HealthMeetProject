package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.HealthMeetProject.code.util.DoctorExampleFixtures.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AvailabilityScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AvailabilityScheduleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private final DoctorService doctorService;
    @MockBean
    private final DoctorEntityMapper doctorEntityMapper;
    @MockBean
    private final AvailabilityScheduleService availabilityScheduleService;
    @MockBean
    private final AvailabilityScheduleDAO availabilityScheduleDAO;
    @MockBean
    private final DoctorMapper doctorMapper;


   @Test
    void testShowYourAvailableTerms() throws Exception {
        // Arrange
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("doctor@example.com");

        List<AvailabilityScheduleDTO> availabilitySchedules = new ArrayList<>();
        // Add availability schedule DTOs to the list

        when(doctorService.authenticateDoctor()).thenReturn("doctor@example.com");
        when(doctorMapper.mapToDTO(doctorService.findByEmail("doctor@example.com"))).thenReturn(doctorDTO);
        when(availabilityScheduleService.findAllTermsByGivenDoctor("doctor@example.com")).thenReturn(availabilitySchedules);

        // Act and Assert
        mockMvc.perform(get("/doctor"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor"))
                .andExpect(model().attribute("doctor", doctorDTO))
                .andExpect(model().attribute("doctorTermsSorted", availabilitySchedules))
                .andExpect(model().attributeExists("formattedSince", "formattedToWhen", "reserved"));
    }

    @Test
    public void testAddTerms() throws Exception {

        String email = "g.shelby@gmail.com";
        when(doctorService.authenticateDoctor()).thenReturn(email);
        when(doctorService.findByEmail(email)).thenReturn(doctorExample3());
        doctorEntityExample3().setDoctorId(1);
        when(doctorEntityMapper.mapToEntity(doctorExample3())).thenReturn(doctorEntityExample3());
        when(doctorMapper.mapToDTO(doctorService.findByEmail(email))).thenReturn(DoctorExampleFixtures.doctorDTOExample3());
        when(availabilityScheduleService.addTerm(availabilityScheduleDTO1().getSince(),
                availabilityScheduleDTO1().getToWhen(), doctorEntityExample3())).thenReturn(availabilityScheduleDTO1());
        when(availabilityScheduleDAO.addTerm(availabilityScheduleDTO1().getSince(),
                availabilityScheduleDTO1().getToWhen(), doctorEntityExample3())).thenReturn(availabilitySchedule1());

        mockMvc.perform(post("/doctor/add/terms")
                        .param("since", "2025-08-06 08:30 AM")
                        .param("toWhen", "2025-08-06 04:00 PM"))
                .andExpect(redirectedUrl("/doctor"))
                .andExpect(view().name("redirect:/doctor"));

    }

    @Test
    public void testDeleteTerm() throws Exception {
        doNothing().when(availabilityScheduleDAO).deleteById(anyInt());

        mockMvc.perform(delete("/doctor/delete/term/{availability_schedule_id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"))
                .andExpect(view().name("redirect:/doctor"));

        verify(availabilityScheduleDAO, times(1)).deleteById(1);
    }
}