package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MeetingProcessingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MeetingProcessingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorDAO doctorDAO;
    @MockBean
    private MeetingRequestService meetingRequestService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private MeetingRequestDAO meetingRequestDAO;


    @Test
    void thatMeetingRequestProcessingPageIsLoadedCorrectly() throws Exception {
        Integer doctorId = 1;

        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        doctor.setDoctorId(doctorId);
        when(doctorDAO.findById(doctorId)).thenReturn(java.util.Optional.of(doctor));

        String doctorEmail = "doctor@example.com";
        when(doctorService.authenticateDoctor()).thenReturn(doctorEmail);

        List<MeetingRequest> meetingRequests = new ArrayList<>();
        meetingRequests.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
        when(meetingRequestService.availableServiceRequestsByDoctor(doctorEmail)).thenReturn(meetingRequests);
        when(meetingRequestService.availableEndedVisitsByDoctor(doctorEmail)).thenReturn(new ArrayList<>());

        mockMvc.perform(get(MeetingProcessingController.MEETING_REQUESTS, doctorId))
                .andExpect(model().attributeExists("meetingRequests"))
                .andExpect(model().attributeExists("endedVisits"))
                .andExpect(model().attributeExists("meetingRequestsDatesToString"))
                .andExpect(model().attributeExists("endedVisitsToString"))
                .andExpect(model().attributeExists("allUpcomingVisits"))
                .andExpect(model().attributeExists("upcomingVisitsToString"))
                .andExpect(view().name("meeting_processing_doctor"));
    }

    @Test
    void thatMeetingRequestIsConfirmedSuccessfully() throws Exception {
        Integer meetingRequestId = 1;

        doThrow(ProcessingException.class).when(meetingRequestService).executeActionForMeetingRequest(meetingRequestId);

        mockMvc.perform(patch("/doctor/execute-action/{meetingRequestId}", meetingRequestId))
                .andExpect(redirectedUrl(null));

        verify(meetingRequestService, times(1)).executeActionForMeetingRequest(meetingRequestId);
    }
    @Test
    void testConfirmMeetingRequest() throws Exception {
        // Mock data
        MeetingRequestEntity meetingRequestEntity =MeetingRequestsExampleFixtures.meetingRequestDataEntityExample1();

        when(meetingRequestService.executeActionForMeetingRequest(1)).thenReturn(meetingRequestEntity);

        mockMvc.perform(patch("/doctor/execute-action/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"))
                .andDo(print());
    }

    @Test
    void testFindByPatientEmail() throws Exception {
        // Mock data
        List<MeetingRequest> meetingRequests = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setCompletedDateTime(null);
        meetingRequest.setVisitEnd(now.plusHours(1));
        meetingRequest.setVisitStart(now.plusMinutes(45));
        meetingRequests.add(meetingRequest);

        when(doctorService.authenticateDoctor()).thenReturn("doctor@example.com");
        when(meetingRequestService.availableServiceRequestsByDoctor("doctor@example.com")).thenReturn(meetingRequests);
        when(meetingRequestDAO.findAllEndedUpVisitsByDoctorAndPatient("doctor@example.com", "patient@example.com")).thenReturn(meetingRequests);

        mockMvc.perform(get("/doctor/search-by/email").param("patientEmail", "patient@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("meeting_processing_doctor_searched"))
                .andExpect(model().attributeExists("date"))
                .andExpect(model().attributeExists("meetingRequests"))
                .andExpect(model().attributeExists("allEndedUpVisits"))
                .andDo(print());
    }
}
