package com.HealthMeetProject.code.api.dto;

import lombok.Builder;

@Builder
public record ReceiptDTO(String now, PatientDTO patientDTO, DoctorDTO doctorDTO, Integer meetingId) {
}
