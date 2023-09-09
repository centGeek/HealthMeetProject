package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

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
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;

    @Column(name = "visit_start")
    private OffsetDateTime visitStart;

    @Column(name = "visit_end")
    private OffsetDateTime visitEnd;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

}
