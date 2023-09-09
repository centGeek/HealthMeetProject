package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.api.dto.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Patient {
    private int patientId;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private String phone;
    private Address address;
    private UserData user;


}