package com.HealthMeetProject.code.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
@Table(name = "meeting_request")
@Builder
public class    MeetingRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;

    @Column(name = "meeting_request_number", unique = true)
    private String meetingRequestNumber;

    @Column(name = "received_date_time")
    private LocalDateTime receivedDateTime;

    @Column(name = "completed_date_time")
    private LocalDateTime completedDateTime;

    @Column(name = "visit_start")
    private LocalDateTime visitStart;

    @Column(name = "visit_end")
    private LocalDateTime visitEnd;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @JsonIgnore
    public DoctorEntity getDoctor() {
        return doctor;
    }

    @JsonIgnore
    public PatientEntity getPatient() {
        return patient;
    }
}
