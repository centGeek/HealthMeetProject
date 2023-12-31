package com.HealthMeetProject.code.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "medicine")
@Entity
@ToString(exclude = "medicine")
@Builder
@With
@Table(name = "receipt")
public class ReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_Id")
    private int receiptId;

    @Column(name = "receipt_number", unique = true)
    private String receiptNumber;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicine_id")
    private Set<MedicineEntity> medicine;

    @JsonIgnore
    public PatientEntity getPatient() {
        return patient;
    }

    @JsonIgnore
    public DoctorEntity getDoctor() {
        if (Objects.isNull(doctor)) {
            return null;
        }
        doctor.setMeetingRequests(null);
        return doctor;
    }
}
