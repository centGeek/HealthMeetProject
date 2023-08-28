package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRequest {
    private Integer meetingId;
    private String meetingRequestNumber;
    private LocalDateTime receivedDateTime;
    private LocalDateTime completedDateTime;
    private String description;
    private Doctor doctor;
    private Patient patient;
    private OffsetDateTime sinceWhenVisit;
    private OffsetDateTime toWhenVisit;


}
