package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.AddressEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ClinicEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;

import java.math.BigDecimal;
import java.util.Set;

public class PatientExampleData {
    public static PatientEntity patientExample1() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
                        .country("Poland")
                        .postalCode("96-323")
                        .address("Niewiadomska 13")
                        .build())
                .name("Jan")
                .surname("Kowalski")
                .email("j.kowalski@gmail.com")
                .phone("+48 783 323 323")
                .pesel("3232223212")
                .build();
    }

    public static PatientEntity patientExample2() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
                        .country("England")
                        .postalCode("323-321")
                        .address("English 13")
                        .build())
                .name("Tommy")
                .surname("Shelby")
                .email("t.shelby@gmail.com")
                .phone("+44 782 323 323")
                .pesel("323223223")
                .build();
    }

    public static PatientEntity patientExample3() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
                        .country("Germany")
                        .postalCode("23-321")
                        .address("a")
                        .build())
                .name("Grace")
                .surname("Shelby")
                .email("g.shelby@gmail.com")
                .phone("+44 722 323 313")
                .pesel("32312321")
                .build();
    }
}