package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;

public class MeetingRequestsExampleFixtures {
 public static MeetingRequestEntity meetingRequestDataExample1() {

     return MeetingRequestEntity.builder()
                .meetingRequestNumber("3213231")
                .receivedDateTime(DoctorExampleFixtures.availabilitySchedule1().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilitySchedule1().getToWhen())
                .description("Zaba mi sie do d... przykleila")
                .build();
    }
    public static MeetingRequestEntity meetingRequestDataExample2() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("2132313")
                .receivedDateTime(DoctorExampleFixtures.availabilitySchedule2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilitySchedule2().getToWhen())
                .description("Druga zaba mi sie do d... przykleila")
                .build();
    }  public static MeetingRequestEntity meetingRequestDataExample3() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("1323132")
                .receivedDateTime(DoctorExampleFixtures.availabilitySchedule3().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilitySchedule3().getToWhen())
                .description("Trzecia zaba mi sie do d... przykleila")
                .build();
    }  public static MeetingRequestEntity meetingRequestDataExample4() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("321323123")
                .receivedDateTime(DoctorExampleFixtures.availabilitySchedule2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilitySchedule3().getToWhen())
                .description("Boli mnie glowa")
                .build();
    }

}
