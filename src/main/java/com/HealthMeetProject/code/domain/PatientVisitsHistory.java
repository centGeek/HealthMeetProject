package com.HealthMeetProject.code.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientVisitsHistory {
    String email;
    List<MeetingRequests> meetingRequests;

    @Value
    @Builder
    @ToString(of = {"meetingRequestNumber", "receivedDateTime", "completedDateTime"})
    public static class MeetingRequests {
        String meetingRequestNumber;
        LocalDateTime receivedDateTime;
        LocalDateTime completedDateTime;
        String description;
        Doctor doctor;
        Patient patient;
        BigDecimal meetingCost;
    }

}
