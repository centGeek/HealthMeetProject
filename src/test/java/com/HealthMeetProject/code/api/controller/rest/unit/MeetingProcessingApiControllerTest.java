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
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(meetingRequestService.restAvailableServiceRequestsByDoctor("j.kowalski@gmail.com")).thenReturn(meetingRequests);

        //when,then
        mockMvc.perform(get(MeetingProcessingApiController.BASE_PATH+
                        "/upcoming-visits/"+doctorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        verify(doctorDAO, times(1)).findById(doctorId);
        verify(meetingRequestService, times(1)).restAvailableServiceRequestsByDoctor("j.kowalski@gmail.com");
    }

    @Test
    public void testFindEndedVisitsByPatientEmail() throws Exception {
        //given
        List<MeetingRequest> meetingRequests = new ArrayList<>();
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        meetingRequest.setMeetingId(1);
        meetingRequests.add(meetingRequest);
        when(meetingRequestDAO.restFindAllEndedUpVisitsByDoctorAndPatient(any(String.class), any(String.class))).thenReturn(meetingRequests);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(MeetingProcessingApiController.BASE_PATH + "/ended-visits/{patientEmail}", meetingRequest.getPatient().getEmail())
                        .param("doctorEmail", "j.kowalski@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(meetingRequestDAO, times(1)).restFindAllEndedUpVisitsByDoctorAndPatient("j.kowalski@gmail.com", meetingRequest.getPatient().getEmail());
    }
    @Test
    public void testConfirmMeetingRequest() throws Exception {
        //given
        Integer meetingRequestId = 1;
        MeetingRequestEntity meetingRequestEntity = MeetingRequestsExampleFixtures.meetingRequestDataEntityExample1();
        when(meetingRequestService.executeActionForMeetingRequest(meetingRequestId)).thenReturn(meetingRequestEntity);

        //when,then
        mockMvc.perform(MockMvcRequestBuilders.patch(MeetingProcessingApiController.BASE_PATH
                        +"/{meetingRequestId}", meetingRequestId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        verify(meetingRequestService, times(1)).executeActionForMeetingRequest(meetingRequestId);
    }
}
