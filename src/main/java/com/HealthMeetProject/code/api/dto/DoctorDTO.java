package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Specialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private Integer doctorId;
    private String name;
    private String surname;
    private Specialization specialization;
    @Email
    private String email;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private BigDecimal salaryFor15minMeet;
    private UserData userData;
    private Clinic clinic;

    public static DoctorDTO buildDefaultData() {
        return DoctorDTO.builder()
                .clinic(Clinic.builder()
                        .clinicName("HealthMeet")
                        .country("Poland")
                        .postalCode("96-323")
                        .address("Niewiadomska 13")
                        .build())
                .name("Jan")
                .email("j.kowalski@gmail.com")
                .surname("Kowalski")
                .phone("+48 783 323 323")
                .specialization(Specialization.PSYCHIATRIST)
                .salaryFor15minMeet(BigDecimal.ONE)
                .build();
    }

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        ofNullable(buildDefaultData().getName()).ifPresent(value -> result.put("name", value));
        ofNullable(buildDefaultData().getSurname()).ifPresent(value -> result.put("surname", value));
        ofNullable(buildDefaultData().getPhone()).ifPresent(value -> result.put("phone", value));
        ofNullable(buildDefaultData().getEmail()).ifPresent(value -> result.put("email", value));
        ofNullable(buildDefaultData().getSalaryFor15minMeet()).ifPresent(value -> result.put("salaryFor15minMeet", value.toString()));
        ofNullable(buildDefaultData().getSpecialization()).ifPresent(value -> result.put("specialization", value.toString()));
        return result;
    }
}
