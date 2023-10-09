package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class MeetingRequestsExampleFixtures {
 public static MeetingRequestEntity meetingRequestDataEntityExample1() {

     return MeetingRequestEntity.builder()
                .meetingRequestNumber("3213231")
                .receivedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getToWhen())
                .visitStart(DoctorExampleFixtures.availabilityScheduleEntity1().getSince())
                .visitEnd(DoctorExampleFixtures.availabilityScheduleEntity1().getToWhen())
                .description("Zaba mi sie do d... przykleila")
                .patient(PatientExampleFixtures.patientEntityExample1())
                .doctor(DoctorExampleFixtures.doctorEntityExample1())
                .build();
    }

    public static MeetingRequestEntity meetingRequestDataEntityExample2() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("2132313")
                .receivedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getToWhen())
                .visitStart(DoctorExampleFixtures.availabilityScheduleEntity1().getSince())
                .visitEnd(DoctorExampleFixtures.availabilityScheduleEntity1().getToWhen())
                .description("Druga zaba mi sie do d... przykleila")

                .build();
    }  public static MeetingRequestEntity meetingRequestDataEntityExample3() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("1323132")
                .receivedDateTime(DoctorExampleFixtures.availabilityScheduleEntity3().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilityScheduleEntity3().getToWhen())
                .visitStart(DoctorExampleFixtures.availabilityScheduleEntity1().getSince())
                .visitEnd(DoctorExampleFixtures.availabilityScheduleEntity1().getToWhen())
                .description("Trzecia zaba mi sie do d... przykleila")
                .build();
    }  public static MeetingRequestEntity meetingRequestDataEntityExample4() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("321323123")
                .receivedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilityScheduleEntity3().getToWhen())
                .description("Boli mnie glowa")
                .visitStart(LocalDateTime.now().minusHours(5))
                .visitEnd(LocalDateTime.now().minusHours(4).minusMinutes(45))
                .patient(PatientExampleFixtures.patientEntityExample2())
                .doctor(DoctorExampleFixtures.doctorEntityExample1())
                .build();
    }
    public static MeetingRequest meetingRequestDataExample1() {
        return MeetingRequest.builder()
                .meetingRequestNumber("321323123")
                .receivedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilityScheduleEntity3().getToWhen())
                .visitStart(LocalDateTime.now())
                .visitEnd(LocalDateTime.now().plusMinutes(15L))
                .description("Boli mnie glowa")
                .patient(PatientExampleFixtures.patientExample1())
                .doctor(DoctorExampleFixtures.doctorExample1())
                .build();
    }
    public static MeetingRequest meetingRequestDataExample2() {
        return MeetingRequest.builder()
                .meetingRequestNumber("2132313")
                .receivedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince())
                .completedDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getToWhen())
                .visitStart(DoctorExampleFixtures.availabilityScheduleEntity1().getSince())
                .visitEnd(DoctorExampleFixtures.availabilityScheduleEntity1().getToWhen())
                .description("Druga zaba mi sie do d... przykleila")
                .build();
    }
}
