package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "name")
    private String surname;

    @Column(name = "surname")
    private String name;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "clinic_id")
    private AddressEntity clinicId;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<VisitInvoiceEntity> invoices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<NoteEntity> notes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<MeetingRequestEntity> meetingRequests;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<ReceiptEntity> receipts;


}