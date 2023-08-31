package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Address;
import com.HealthMeetProject.code.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private String phone;
    private Address address;
    private UserData userData;
}
