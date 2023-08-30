package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "phone")
    private String phone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<VisitInvoiceEntity> invoices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<NoteEntity> notes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<MeetingRequestEntity> meetingRequests;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<ReceiptEntity> receipts;

}