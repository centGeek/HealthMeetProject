package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
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
    private OffsetDateTime since;

    @Column(name = "to_when")
    private OffsetDateTime toWhen;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

}
