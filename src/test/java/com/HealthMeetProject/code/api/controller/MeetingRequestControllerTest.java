package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MeetingRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestControllerTest {
    private MockMvc mockMvc;


    @MockBean
    private final AvailabilityScheduleService availabilityScheduleService;
    @MockBean
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    @MockBean
    private final MeetingRequestService meetingRequestService;
    @MockBean
    private final PatientService patientService;
    @MockBean
    private final PatientDAO patientDAO;
    @MockBean
    private final AvailabilityScheduleDAO availabilityScheduleDAO;



    @Test
    void testChooseAccurateTerm() throws Exception {
        //given
        AvailabilitySchedule availabilitySchedule = DoctorExampleFixtures.availabilitySchedule1();


        AvailabilityScheduleDTO availabilityScheduleDTO = new AvailabilityScheduleDTO();
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
    void finalizeMeetingRequestTest(){

    }

}