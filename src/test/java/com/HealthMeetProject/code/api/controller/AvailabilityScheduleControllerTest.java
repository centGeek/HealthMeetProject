package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static com.HealthMeetProject.code.util.DoctorExampleFixtures.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AvailabilityScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AvailabilityScheduleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private final DoctorService doctorService;

    @MockBean
    private AvailabilityScheduleService availabilityScheduleService;

    @MockBean
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;

    @MockBean
    private final DoctorEntityMapper doctorEntityMapper;

    @Test
    void doctorAvailabilityPageProperlyDisplayed() throws Exception {
        String email = "j.kowalski@gmail.com";
        when(doctorService.authenticateDoctor()).thenReturn(email);
        when(doctorService.findByEmail(email)).thenReturn(doctorExample3());
        when(availabilityScheduleService.findAllTermsByGivenDoctor(email)
                .stream().sorted(Comparator.comparing(AvailabilityScheduleDTO::getSince)).toList()).thenReturn(List.of(
                        availabilityScheduleDTO1(), availabilityScheduleDTO2(), availabilityScheduleDTO3()));

        mockMvc.perform(get(DoctorController.DOCTOR))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorTermsSorted"))
                .andExpect(model().attributeExists("formattedSince"))
                .andExpect(model().attributeExists("formattedToWhen"))
                .andExpect(model().attributeExists("reserved"))
                .andExpect(view().name("doctor"));
    }
    @Test
    public void testAddTerms() throws Exception {

        String email = "g.shelby@gmail.com";
        when(doctorService.authenticateDoctor()).thenReturn(email);
        when(doctorService.findByEmail(email)).thenReturn(doctorExample3());
        doctorEntityExample3().setDoctorId(1);
        when(doctorEntityMapper.mapToEntity(doctorExample3())).thenReturn(doctorEntityExample3());
        when(availabilityScheduleService.addTerm(availabilityScheduleDTO1().getSince(),
                availabilityScheduleDTO1().getToWhen(), doctorEntityExample3())).thenReturn(availabilityScheduleDTO1());

        mockMvc.perform(post("/doctor/add/terms")
                        .param("since", "2025-05-01 08:30 AM")
                        .param("toWhen", "2025-05-01 04:00 PM"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"))
                .andExpect(view().name("redirect:/doctor"));

    }
    @Test
    public void testDeleteTerm() throws Exception {
        doNothing().when(availabilityScheduleJpaRepository).deleteById(anyInt());

        mockMvc.perform(delete("/doctor/delete/term/{availability_schedule_id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"))
                .andExpect(view().name("redirect:/doctor"));

        verify(availabilityScheduleJpaRepository, times(1)).deleteById(1);
    }
}