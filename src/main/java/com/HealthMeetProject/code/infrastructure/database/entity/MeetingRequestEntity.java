package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
@Table(name = "meeting_request")
@Builder
public class MeetingRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;

    @Column(name = "meeting_request_number", unique = true)
    private String meetingRequestNumber;
    @Column(name = "received_date_time")
    private LocalDateTime receivedDateTime;
    @Column(name = "completed_date_time")
    private LocalDateTime completedDateTime;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

}
