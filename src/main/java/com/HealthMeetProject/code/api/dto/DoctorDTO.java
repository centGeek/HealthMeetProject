package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private String name;
    private String surname;
    private Specialization specialization;
    private String email;
    private String phone;
    private BigDecimal salaryFor15minMeet;
    private Clinic clinic;
}
