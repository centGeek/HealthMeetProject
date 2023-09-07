package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private final MeetingRequestService meetingRequestService;
    @MockBean
    private final NoteJpaRepository noteJpaRepository;
    @MockBean
    private final PatientService patientService;

    @Test
    void thatPatientHistoryPageIsLoadedCorrectly() throws Exception {
//        Mockito.when()


        mockMvc.perform(get(PatientController.PATIENT_HISTORY))
                .andExpect(model().attributeExists("allCompletedServiceRequestsByEmail"))
                .andExpect(model().attributeExists("byPatientEmail"))
                .andExpect(model().attributeExists("totalCosts"));
    }
}