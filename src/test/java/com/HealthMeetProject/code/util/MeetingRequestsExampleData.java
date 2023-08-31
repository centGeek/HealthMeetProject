package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;

public class MeetingRequestsExampleData {
 public static MeetingRequestEntity meetingRequestDataExample1() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("3213231")
                .receivedDateTime(DoctorExampleData.availabilitySchedule1().getSince().toLocalDateTime())
                .completedDateTime(DoctorExampleData.availabilitySchedule1().getToWhen().toLocalDateTime())
                .description("Zaba mi sie do d... przykleila")
                .doctor(DoctorExampleData.doctorExample1())
                .patient(PatientExampleData.patientExample1())
                .build();
    }
    public static MeetingRequestEntity meetingRequestDataExample2() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("2132313")
                .receivedDateTime(DoctorExampleData.availabilitySchedule2().getSince().toLocalDateTime())
                .completedDateTime(DoctorExampleData.availabilitySchedule2().getToWhen().toLocalDateTime())
                .description("Druga zaba mi sie do d... przykleila")
                .doctor(DoctorExampleData.doctorExample2())
                .patient(PatientExampleData.patientExample2())
                .build();
    }  public static MeetingRequestEntity meetingRequestDataExample3() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("1323132")
                .receivedDateTime(DoctorExampleData.availabilitySchedule3().getSince().toLocalDateTime())
                .completedDateTime(DoctorExampleData.availabilitySchedule3().getToWhen().toLocalDateTime())
                .description("Trzecia zaba mi sie do d... przykleila")
                .doctor(DoctorExampleData.doctorExample3())
                .patient(PatientExampleData.patientExample3())
                .build();
    }  public static MeetingRequestEntity meetingRequestDataExample4() {
        return MeetingRequestEntity.builder()
                .meetingRequestNumber("321323123")
                .receivedDateTime(DoctorExampleData.availabilitySchedule2().getSince().toLocalDateTime())
                .completedDateTime(DoctorExampleData.availabilitySchedule3().getToWhen().toLocalDateTime())
                .description("Boli mnie glowa")
                .doctor(DoctorExampleData.doctorExample2())
                .patient(PatientExampleData.patientExample3())
                .build();
    }

}
