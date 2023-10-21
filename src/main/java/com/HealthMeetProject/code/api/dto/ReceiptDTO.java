package com.HealthMeetProject.code.api.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceiptDTO {
    String now;
    PatientDTO patientDTO;
    DoctorDTO doctorDTO;
    Integer meetingId;
}
