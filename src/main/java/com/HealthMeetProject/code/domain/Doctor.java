package com.HealthMeetProject.code.domain;

import java.math.BigDecimal;
import java.util.Set;


public class Doctor {


    private int doctorId;


    private String name;


    private String surname;


    private Specialization specialization;


    private String email;


    private String phone;


    private BigDecimal salaryFor15minMeet;



    private Address clinic;


    private Set<VisitInvoice> invoices;


    private Set<AvailabilitySchedule> terms;


    private Set<MeetingRequest> meetingRequests;


    private Set<Receipt> receipts;

}
