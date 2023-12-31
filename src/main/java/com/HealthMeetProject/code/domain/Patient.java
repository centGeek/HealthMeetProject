package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.api.dto.UserData;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
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