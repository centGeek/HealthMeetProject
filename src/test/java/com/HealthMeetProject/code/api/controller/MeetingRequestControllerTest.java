package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MeetingRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;

    @MockBean
    private final DoctorEntityMapper doctorEntityMapper;
    @MockBean
    private final MeetingRequestService meetingRequestService;
    @MockBean
    private final PatientService patientService;
    @MockBean
    private final PatientJpaRepository patientJpaRepository;
    @MockBean
    private final PatientEntityMapper patientEntityMapper;


    @Test
    public void testChooseAccurateTerm() throws Exception {
        int availabilityScheduleId = 10;
        Mockito.when(availabilityScheduleJpaRepository.findById(availabilityScheduleId))
                .thenReturn(Optional.of(DoctorExampleFixtures.availabilitySchedule1()));
        mockMvc.perform(get("/patient/terms/appointment/10"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("particularVisitTime"))
                .andExpect(view().name("make_appointment"));
    }

}