package com.HealthMeetProject.code.infrastructure.database.entity;

import com.HealthMeetProject.code.domain.Specialization;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "doctor")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "specialization")
    private Specialization specialization;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary_for_15min_meet")
    private BigDecimal salaryFor15minMeet;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clinic_id")
    private ClinicEntity clinic;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<VisitInvoiceEntity> invoices;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor",  cascade = CascadeType.ALL)
    private Set<MeetingRequestEntity> meetingRequests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor",  cascade = CascadeType.ALL)
    private Set<ReceiptEntity> receipts;
}