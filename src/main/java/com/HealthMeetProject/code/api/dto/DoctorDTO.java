package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private String name;
    private String surname;
    private Specialization specialization;
    private String email;
    private String phone;
    private BigDecimal salaryFor15minMeet;
    private UserData userData;
    private Clinic clinic;
}
