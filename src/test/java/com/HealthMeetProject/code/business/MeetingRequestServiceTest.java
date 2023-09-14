package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MeetingRequestServiceTest {
    @Mock
    private AvailabilityScheduleMapper availabilityScheduleMapper;
    @Mock
    private DoctorMapper doctorMapper;
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
    public void thatMeetingCanBeCanceledTest(){
        OffsetDateTime visitStart1 = OffsetDateTime.now().plusHours(1);
        OffsetDateTime visitStart2 = OffsetDateTime.now().plusMinutes(30).plusSeconds(1);
        boolean result1 = meetingRequestService.canCancelMeeting(visitStart1);
        boolean result2 = meetingRequestService.canCancelMeeting(visitStart2);

        Assertions.assertThat(result1).isEqualTo(true);
        Assertions.assertThat(result2).isEqualTo(false);
    }

    @Test
    public void onlyOneMeetingRequest(){
        String email = "goraca_foka@o2.com";
        List<MeetingRequest> meetingRequestList = List.of();
        Mockito.when(meetingRequestDAO.findAllActiveMeetingRequests(email)).thenReturn(meetingRequestList);

        assertDoesNotThrow(() -> meetingRequestService.validate(email));
    }
    @Test
    public void moreMeetingRequestsInTheSameTime(){
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
}