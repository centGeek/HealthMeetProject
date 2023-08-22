package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visit_invoice")
public class VisitInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "invoice_nr")
    private String invoiceNr;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "visit_cost")
    private String visitCost;

    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.EAGER)
    private DoctorEntity doctor;
}
