package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private int patientId;
    private String surname;
    private String name;
    private String pesel;
    private String email;
    private String phone;
    private Address address;
    private Set<VisitInvoice> invoices;
    private Set<Note> notes;
    private Set<MeetingRequest> meetingRequests;
    private Set<Receipt> receipts;


}