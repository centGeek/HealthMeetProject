package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "doctor")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "availability_schedule")
@Builder
public class AvailabilityScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int availability_schedule_id;

    @Column(name = "since")
    private LocalDateTime since;

    @Column(name = "to_when")
    private LocalDateTime toWhen;

    @Column(name = "available_day")
    private boolean availableDay;

    @Column(name = "available_term")
    private boolean availableTerm;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

}
