package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clinic")
public class ClinicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer clinicId;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "clinic")
    private DoctorEntity doctorEntity;



}
