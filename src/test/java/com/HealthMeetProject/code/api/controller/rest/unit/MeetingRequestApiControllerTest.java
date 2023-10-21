package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.MeetingRequestApiController;
import com.HealthMeetProject.code.api.dto.MeetingRequestDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class MeetingRequestApiControllerTest {

    @Mock
    private AvailabilityScheduleService availabilityScheduleService;

    @Mock
    private AvailabilityScheduleDAO availabilityScheduleDAO;
    @Mock
    private  AvailabilityScheduleMapper availabilityScheduleMapper;


    @Mock
    private MeetingRequestService meetingRequestService;

    @Mock
    private PatientService patientService;

    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private MeetingRequestApiController meetingRequestApiController;


//    @Test
//    public void testFinalizeMeetingRequest() {
//        Integer availabilityScheduleId = 1;
//        Integer selectedSlotId = 2;
//        when(meetingRequestService.getAvailabilitySchedule(availabilityScheduleId, selectedSlotId)).thenReturn(DoctorExampleFixtures.availabilityScheduleDTO1());
//        when(availabilityScheduleDAO.findById(availabilityScheduleId)).thenReturn(DoctorExampleFixtures.availabilitySchedule1());
//
//        ResponseEntity<?> responseEntity = meetingRequestApiController.finalizeMeetingRequest(availabilityScheduleId, selectedSlotId);
//
//        verify(meetingRequestService, times(1)).getAvailabilitySchedule(availabilityScheduleId, selectedSlotId);
//        verify(availabilityScheduleService, times(1)).save(any(AvailabilitySchedule.class));
//
//        assertEquals(200, responseEntity.getStatusCodeValue());
//    }

    @Test
    public void testAddMeetingRequest() {
        Integer availabilityScheduleId = 1;
        String description = "Test description";
        when(availabilityScheduleDAO.findById(availabilityScheduleId)).thenReturn(DoctorExampleFixtures.availabilitySchedule1());
        when(patientDAO.findByEmail("patient@example.com")).thenReturn(new Patient());
        when(availabilityScheduleMapper.mapToDTO(any(AvailabilitySchedule.class))).thenReturn(DoctorExampleFixtures.availabilityScheduleDTO1());
        MeetingRequestDTO meetingRequestDTO = MeetingRequestDTO.builder().patientEmail("patient@example.com").description("someDescription")
                .availabilityScheduleId(availabilityScheduleId).build();
        ResponseEntity<?> responseEntity = meetingRequestApiController.addMeetingRequest(meetingRequestDTO);
        verify(patientDAO, times(1)).findByEmail("patient@example.com");

        //noinspection deprecation
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
}
