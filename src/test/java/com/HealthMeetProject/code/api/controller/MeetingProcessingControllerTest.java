package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(view().name("meeting_processing_doctor"));
    }

    @Test
    void thatMeetingRequestIsConfirmedSuccessfully() throws Exception {
        Integer meetingRequestId = 1;

        doNothing().when(meetingRequestService).executeActionForMeetingRequest(meetingRequestId);

        mockMvc.perform(patch("/doctor/execute-action/{meetingRequestId}", meetingRequestId))
                .andExpect(redirectedUrl("/doctor"));

        verify(meetingRequestService, times(1)).executeActionForMeetingRequest(meetingRequestId);
    }
}
