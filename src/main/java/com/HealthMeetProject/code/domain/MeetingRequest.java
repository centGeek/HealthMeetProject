package com.HealthMeetProject.code.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

}
