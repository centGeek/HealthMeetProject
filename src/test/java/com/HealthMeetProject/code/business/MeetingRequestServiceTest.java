package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MeetingRequestServiceTest {
    @Mock
    private AvailabilityScheduleMapper availabilityScheduleMapper;
    @Mock
    private DoctorMapper doctorMapper;
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private PatientService patientService;
    @Mock
    private MeetingRequestDAO meetingRequestDAO;
    @Mock
    private MeetingRequestJpaRepository meetingRequestJpaRepository;
    @Mock
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    @Mock
    private AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    @Mock
    private MeetingRequestEntityMapper meetingRequestEntityMapper;
    @InjectMocks
    private MeetingRequestService meetingRequestService;

    @Test
    public void thatMeetingCanBeCanceledTest() {
        LocalDateTime visitStart1 = LocalDateTime.now().plusHours(1);
        LocalDateTime visitStart2 = LocalDateTime.now().plusMinutes(30).plusSeconds(1);
        boolean result1 = meetingRequestService.canCancelMeeting(visitStart1);
        boolean result2 = meetingRequestService.canCancelMeeting(visitStart2);

        Assertions.assertThat(result1).isEqualTo(true);
        Assertions.assertThat(result2).isEqualTo(false);
    }

    @Test
    public void onlyOneMeetingRequest() {
        String email = "goraca_foka@o2.com";
        List<MeetingRequest> meetingRequestList = List.of();
        Mockito.when(meetingRequestDAO.findAllActiveMeetingRequests(email)).thenReturn(meetingRequestList);

        assertDoesNotThrow(() -> meetingRequestService.validate(email));
    }

    @Test
    public void moreMeetingRequestsInTheSameTime() {
        String email = "j.kowalski@gmail.com";
        List<MeetingRequest> meetingRequestList = List.of(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
        Mockito.when(meetingRequestDAO.findAllActiveMeetingRequests(email)).thenReturn(meetingRequestList);

        RuntimeException exception = assertThrows(ProcessingException.class, () ->
                meetingRequestService.validate(email));

        String expectedMessage = "There should be only one active meeting request at a time, patient email: [%s]".formatted(email);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCanNotCancelMeetingList() {
        //given
        List<MeetingRequest> allUpcomingVisits = new ArrayList<>();
        allUpcomingVisits.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
        //when
        List<Boolean> canCancelMeetingList = meetingRequestService.canCancelMeetingList(allUpcomingVisits);

        //then
        for (Boolean canCancelMeeting : canCancelMeetingList) {
            assertEquals(false, canCancelMeeting);
        }
    }

    @Test
    void thatMeetingRequestIsMadeCorrectly() {
        //given
        DoctorDTO doctorDTO = DoctorDTOFixtures.getDoctorDTO1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        doctor.setUser(DoctorDTOFixtures.getUserDTO1());

        Patient patient = PatientExampleFixtures.patientExample1();
        patient.setUser(PatientDTOFixtures.userDataPatient());
        AvailabilityScheduleDTO availabilityScheduleDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
        AvailabilitySchedule availabilitySchedule = DoctorExampleFixtures.availabilitySchedule1();

        //when
        Mockito.when(availabilityScheduleMapper.mapFromDTO(availabilityScheduleDTO))
                .thenReturn(availabilitySchedule);
        Mockito.when(doctorMapper.mapFromDTO(doctorDTO)).thenReturn(doctor);

        //then
        MeetingRequest meetingRequestToCheck = meetingRequestService.makeMeetingRequest(patient, doctorDTO, "Siema to jan", availabilityScheduleDTO);

        assertEquals(meetingRequestToCheck.getVisitEnd(), availabilitySchedule.getToWhen());
        assertEquals(meetingRequestToCheck.getDoctor().getEarningsPerVisit(), doctor.getEarningsPerVisit());
        assertEquals(meetingRequestToCheck.getDoctor().getSurname(), doctor.getSurname());
        assertEquals(meetingRequestToCheck.getDoctor().getUser().getUserName(), doctor.getUser().getUserName());
    }

    @Test
    public void testGenerateTimeSlots() {
        //given
        LocalDateTime since = LocalDateTime.of(2023, 10, 5, 9, 0);
        LocalDateTime toWhen = LocalDateTime.of(2023, 10, 5, 12, 0);
        Doctor doctor = DoctorExampleFixtures.doctorExample1();

        //when
        List<AvailabilitySchedule> timeSlots = meetingRequestService.generateTimeSlots(since, toWhen, doctor);

        int expectedSlotCount = 12;
        assertEquals(expectedSlotCount, timeSlots.size());

        LocalDateTime currentSlot = since;
        for (AvailabilitySchedule slot : timeSlots) {
            assertEquals(currentSlot, slot.getSince());
            assertEquals(currentSlot.plusMinutes(15), slot.getToWhen());
            currentSlot = currentSlot.plusMinutes(15);
        }
    }


    @Test
    public void testAvailableEndedVisitsByDoctor() {
        //given
        String email = "doctor@example.com";
        MeetingRequest request1 =MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        MeetingRequest request2 = MeetingRequestsExampleFixtures.meetingRequestDataExample2();
        List<MeetingRequest> requests = Arrays.asList(request1, request2);

       //when
        Mockito.when(meetingRequestDAO.availableEndedVisitsByDoctor(email)).thenReturn(requests);

        //when
        List<MeetingRequest> result = meetingRequestService.availableEndedVisitsByDoctor(email);

        //then
        assertEquals(requests.size(), result.size());
    }

    @Test
    public void testFindById() {
        //given
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        MeetingRequestEntity meetingRequestEntity = MeetingRequestsExampleFixtures.meetingRequestDataEntityExample1();
        Mockito.when(meetingRequestJpaRepository.findById(any())).thenReturn(Optional.of(meetingRequestEntity));
        Mockito.when(meetingRequestEntityMapper.mapFromEntity(meetingRequestEntity)).thenReturn(meetingRequest);

        //when
        MeetingRequest result = meetingRequestService.findById(3);

        //then
        assertEquals(meetingRequest, result);
    }
    @Test
    public void testExecuteActionForMeetingRequest() {
        //given
        int meetingRequestId = 1;
        MeetingRequestEntity meetingRequestEntity = new MeetingRequestEntity();
        Mockito.when(meetingRequestJpaRepository.findById(meetingRequestId)).thenReturn(Optional.of(meetingRequestEntity));

        //when
        MeetingRequestEntity result = meetingRequestService.executeActionForMeetingRequest(meetingRequestId);
        //then
        assertEquals(meetingRequestEntity, result);
    }

    @Test
    public void testFindAllCompletedServiceRequestsByEmail() {
        //given
        String email = "patient@example.com";
        MeetingRequest request1 = new MeetingRequest();
        MeetingRequest request2 = new MeetingRequest();
        List<MeetingRequest> requests = Arrays.asList(request1, request2);

        //when
        Mockito.when(meetingRequestDAO.findAllCompletedServiceRequestsByEmail(email)).thenReturn(requests);

        List<MeetingRequest> result = meetingRequestService.findAllCompletedServiceRequestsByEmail(email);

        //when
        assertEquals(requests.size(), result.size());
    }

    @Test
    public void testAvailableServiceRequestsByDoctor() {
        //given
        String email = "doctor@example.com";
        MeetingRequest request1 = new MeetingRequest();
        MeetingRequest request2 = new MeetingRequest();
        List<MeetingRequest> requests = Arrays.asList(request1, request2);
        //when
        Mockito.when(meetingRequestDAO.availableServiceRequests(email)).thenReturn(requests);
        List<MeetingRequest> result = meetingRequestService.availableServiceRequestsByDoctor(email);
        //then
        assertEquals(requests.size(), result.size());
    }
    @Test
    public void testGenerateNumber() {
        //given
        LocalDateTime when = LocalDateTime.of(2023, 10, 5, 15, 30, 45);
        String generatedNumber = meetingRequestService.generateNumber(when);
        String[] parts = generatedNumber.split("\\.");

        //when,then
        assertEquals(6, parts.length);

        assertEquals("2023", parts[0]);

        assertEquals("9", parts[1]);

        assertEquals("5-15", parts[2]);

        assertEquals("30", parts[3]);

        assertEquals("45", parts[4]);

        int randomNumber = Integer.parseInt(parts[5]);
        assertTrue(randomNumber >= 10 && randomNumber <= 100);
    }

}

