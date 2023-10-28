package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.business.PatientHistoryDTOService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerTest {


    private MockMvc mockMvc;
    @MockBean
    private final MeetingRequestDAO meetingRequestDAO;
    @MockBean
    private final PatientService patientService;
    @MockBean
    private final PatientDAO patientDAO;
    @MockBean
    private final PatientHistoryDTOService patientHistoryDTOService;


    @Test
    void testGetPatientHistoryPage() throws Exception {
        //given
        PatientHistoryDTO build = PatientHistoryDTO.builder().build();
        when(patientHistoryDTOService.patientHistoryDTO(any())).thenReturn(build);


        //then
        mockMvc.perform(get(PatientController.PATIENT_HISTORY))
                .andExpect(status().isOk())
                .andExpect(view().name("patient_history"))
                .andExpect(model().attribute("patientHistoryDTO", build));
    }


    @Test
    void testShowEditDoctorForm() throws Exception {
        mockMvc.perform(get("/patient/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patient"));
    }

    @Test
    void testUpdateDoctor() throws Exception {

        mockMvc.perform(patch("/patient/1/edit")
                        .param("name", "John")
                        .param("surname", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("phone", "123-456-789")
                        .param("Country", "Polska")
                        .param("City", "Poscien Zamion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));
    }
}