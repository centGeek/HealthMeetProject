package com.HealthMeetProject.code.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitInvoice {
    private Integer invoiceId;

    private String invoiceNr;

    private String startDate;

    private String patientId;

    private String doctorId;

    private String visitCost;

    private Patient patient;

    private Doctor doctor;
}
