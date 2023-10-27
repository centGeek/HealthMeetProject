package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.api.MeetingRequestsDTOs;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MeetingRequestsDTOsTest {
    @Test
    public void availabilityScheduleEquals(){
        MeetingRequestsDTOs meetingRequestDTO = MeetingRequestsDTOs.of(List.of());
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        meetingRequest.setDoctor(null);
        meetingRequestDTO.setMeetingRequestList(List.of(meetingRequest));
        MeetingRequestsDTOs meetingRequestDTO1 = MeetingRequestsDTOs.builder()
                .meetingRequestList(List.of(meetingRequest)).build();
        Assertions.assertEquals(meetingRequestDTO, meetingRequestDTO1);

    }
}