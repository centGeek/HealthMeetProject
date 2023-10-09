package com.HealthMeetProject.code.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
