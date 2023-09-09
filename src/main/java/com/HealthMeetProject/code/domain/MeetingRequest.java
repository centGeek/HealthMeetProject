package com.HealthMeetProject.code.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRequest {
    private Integer meetingId;
    private String meetingRequestNumber;
    private OffsetDateTime receivedDateTime;
    private OffsetDateTime completedDateTime;
    private OffsetDateTime visitStart;
    private OffsetDateTime visitEnd;
    private String description;
    private Doctor doctor;
    private Patient patient;

}
