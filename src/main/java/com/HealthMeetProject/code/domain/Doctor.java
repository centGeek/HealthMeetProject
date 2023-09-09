package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.api.dto.UserData;
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
    private String email;

    private Specialization specialization;

    private String phone;

    private BigDecimal earningsPerVisit;

    private Clinic clinic;

    private UserData user;


    private Set<MeetingRequest> meetingRequests;


}
