package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private Integer patientId;
    private String name;
    private String surname;
    @Email
    private String email;
    private String pesel;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private Address address;
    private UserData user;
}
