package com.HealthMeetProject.code.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class MeetingRequest {
    private Integer meetingId;
    private String meetingRequestNumber;
    private LocalDateTime receivedDateTime;
    private LocalDateTime completedDateTime;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private String description;
    private Doctor doctor;
    private Patient patient;

}
