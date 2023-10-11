package com.HealthMeetProject.code.infrastructure.database.entity;

import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingRequestEntityTest {
    @Test
    public void meetingRequestEntityWitherTest(){
        MeetingRequestEntity meetingRequestEntity = new MeetingRequestEntity();
        MeetingRequestEntity meetingRequestEntity1 = meetingRequestEntity
                .withMeetingId(1)
                .withDescription("meeting")
                .withCompletedDateTime(LocalDateTime.MAX)
                .withReceivedDateTime(LocalDateTime.MAX)
                .withVisitEnd(LocalDateTime.MAX)
                .withVisitStart(LocalDateTime.MAX)
                .withDoctor(DoctorExampleFixtures.doctorEntityExample1())
                .withMeetingRequestNumber("32322355121");
        meetingRequestEntity1.setMeetingId(1);
        Assertions.assertEquals(meetingRequestEntity1.getMeetingId(),1);
        Assertions.assertEquals(meetingRequestEntity1.getMeetingRequestNumber(),"32322355121");
        Assertions.assertEquals(meetingRequestEntity1.getVisitEnd(),LocalDateTime.MAX);

    }
}