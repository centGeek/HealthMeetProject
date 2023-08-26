package com.HealthMeetProject.code.infrastructure.database.entity;

import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "specialization")
    private Specialization specialization;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary_for_15min_meet")
    private BigDecimal salaryFor15minMeet;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "clinic_id")
    private ClinicEntity clinic;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    private Set<VisitInvoiceEntity> invoices;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AvailabilityScheduleEntity> terms;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    private Set<MeetingRequestEntity> meetingRequests;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    private Set<ReceiptEntity> receipts;

}
