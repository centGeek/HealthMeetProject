package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {


    private int doctorId;


    private String name;


    private String surname;


    private Specialization specialization;


    private String email;


    private String phone;


    private BigDecimal salaryFor15minMeet;



    private Clinic clinic;


    private Set<VisitInvoice> invoices;


    private Set<AvailabilitySchedule> terms;


    private Set<MeetingRequest> meetingRequests;


    private Set<Receipt> receipts;

}
