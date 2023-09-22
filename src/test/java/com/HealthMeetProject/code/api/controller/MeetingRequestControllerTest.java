package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MeetingRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityScheduleService availabilityScheduleService;
    @MockBean
    private AvailabilityScheduleMapper availabilityScheduleMapper;
    @MockBean
    private MeetingRequestService meetingRequestService;
    @MockBean
    private PatientService patientService;
    @MockBean
    private PatientDAO patientDAO;
    @MockBean
    private AvailabilityScheduleDAO availabilityScheduleDAO;
    @Mock
    private HttpSession httpSession;

    @Test
    void testChooseAccurateTerm() throws Exception {
        //given
        AvailabilitySchedule availabilitySchedule = DoctorExampleFixtures.availabilitySchedule1();


        AvailabilityScheduleDTO availabilityScheduleDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
        availabilityScheduleDTO.setAvailability_schedule_id(1);

        List<AvailabilitySchedule> particularVisitTime = new ArrayList<>();
        //wwhen
        when(availabilityScheduleDAO.findById(1)).thenReturn(availabilitySchedule);
        when(availabilityScheduleMapper.mapToDTO(availabilitySchedule)).thenReturn(availabilityScheduleDTO);
        when(meetingRequestService.generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), availabilitySchedule.getDoctor())).thenReturn(particularVisitTime);

        // then
        mockMvc.perform(get(MeetingRequestController.MAKE_APPOINTMENT, 1))
                .andExpect(status().isOk())
                .andExpect(view().name("make_appointment"))
                .andExpect(model().attribute("givenAvailabilitySchedule", availabilityScheduleDTO))
                .andExpect(model().attributeExists("particularVisitTime"));
    }
    @Test
    void testFinalizeMeetingRequest() throws Exception {
        // Mock data
        Integer availabilityScheduleId = 1;
        Integer selectedSlotId = 2;

        AvailabilityScheduleDTO availabilityScheduleDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
        availabilityScheduleDTO.setDoctor(DoctorDTOFixtures.getDoctorDTO1());

        AvailabilitySchedule availabilitySchedule = new AvailabilitySchedule();
        availabilitySchedule.setAvailableTerm(true);

        when(meetingRequestService.getAvailabilitySchedule(availabilityScheduleId, selectedSlotId)).thenReturn(availabilityScheduleDTO);
        when(availabilityScheduleDAO.findById(availabilityScheduleId)).thenReturn(availabilitySchedule);

        mockMvc.perform(get("/patient/terms/appointment/1/finalize/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("finalize_meeting_request"))
                .andExpect(model().attributeExists("visitTerm"))
                .andExpect(request().sessionAttribute("visitTerm", availabilityScheduleDTO))
                .andDo(print());
    }

//    @Test
//    void testAddMeetingRequest() throws Exception {
//        // Mock data
//        String description = "Meeting request description";
//        String email = "patient@example.com";
//        Patient patient = PatientExampleFixtures.patientExample1();
//        patient.setEmail(email);
//
//        AvailabilityScheduleDTO availabilityScheduleDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
//        availabilityScheduleDTO.setDoctor(DoctorDTOFixtures.getDoctorDTOToRegister());
//
//        when(patientService.authenticate()).thenReturn(email);
//        when(patientDAO.findByEmail(email)).thenReturn(patient);
//
//        // Ustaw atrybut sesji "visitTerm" na odpowiedni obiekt przed wywołaniem mockMvc.perform
//        when(httpSession.getAttribute("visitTerm")).thenReturn(availabilityScheduleDTO);
//
//        mockMvc.perform(post("/patient/terms/add/meeting_request")
//                        .param("description", description))
//                .andExpect(status().isOk())
//                .andExpect(view().name("meeting_request_finalized"))
//                .andExpect(model().attributeExists("visitTerm"))
//                .andDo(print());
//    }
}