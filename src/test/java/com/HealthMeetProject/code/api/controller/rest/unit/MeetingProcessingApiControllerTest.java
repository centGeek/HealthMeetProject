package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.MeetingProcessingApiController;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
public class MeetingProcessingApiControllerTest {

    @Mock
    private DoctorDAO doctorDAO;

    @Mock
    private MeetingRequestService meetingRequestService;

    @Mock
    private MeetingRequestDAO meetingRequestDAO;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private MeetingProcessingApiController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetWaitingForConfirmationMeetingRequests() throws Exception {
        //given
        Integer doctorId = 1;
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        doctor.setDoctorId(doctorId);
        List<MeetingRequest> meetingRequests = new ArrayList<>();
        when(doctorDAO.findById(doctorId)).thenReturn(java.util.Optional.of(doctor));
        when(meetingRequestService.availableServiceRequestsByDoctor("j.kowalski@gmail.com")).thenReturn(meetingRequests);

        //when,then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/meeting-processing/upcoming-visits/{doctorId}", doctorId)
                        .param("doctorEmail", doctor.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        verify(doctorDAO, times(1)).findById(doctorId);
        verify(meetingRequestService, times(1)).availableServiceRequestsByDoctor("j.kowalski@gmail.com");
    }

    @Test
    public void testFindEndedVisitsByPatientEmail() throws Exception {
        //given
        String patientEmail = "patient@example.com";
        String doctorEmail = "doctor@example.com";
        List<MeetingRequest> meetingRequests = new ArrayList<>();
        when(doctorService.authenticateDoctor()).thenReturn(doctorEmail);
        when(meetingRequestDAO.findAllEndedUpVisitsByDoctorAndPatient(doctorEmail, patientEmail)).thenReturn(meetingRequests);

        //when & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/meeting-processing/ended-visits")
                        .param("patientEmail", patientEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        verify(doctorService, times(1)).authenticateDoctor();
        verify(meetingRequestDAO, times(1)).findAllEndedUpVisitsByDoctorAndPatient(doctorEmail, patientEmail);
    }

    @Test
    public void testConfirmMeetingRequest() throws Exception {
        //given
        Integer meetingRequestId = 1;
        MeetingRequestEntity meetingRequestEntity = MeetingRequestsExampleFixtures.meetingRequestDataEntityExample1();
        when(meetingRequestService.executeActionForMeetingRequest(meetingRequestId)).thenReturn(meetingRequestEntity);

        //when & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/meeting-processing/{meetingRequestId}", meetingRequestId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        verify(meetingRequestService, times(1)).executeActionForMeetingRequest(meetingRequestId);
    }
}
