package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.domain.Address;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;

import java.util.Set;

public class PatientDTOFixtures {
    public static PatientDTO patientDTOExample1() {
        return PatientDTO.builder()
                .address(Address.builder()
                        .country("Poland")
                        .city("Warszawa")
                        .postalCode("96-323")
                        .address("Niewiadomska 13")
                        .build())
                .name("Jan")
                .surname("Kowalski")
                .phone("+48 783 323 323")
                .pesel("3232223212")
                .email("j.kowalski@gmail.com")
                .build();
    }
    public static PatientDTO patientDTOExample2() {
        return PatientDTO.builder()
                .address(Address.builder()
                        .city("London")
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

    public static UserData userDataPatient() {
        return UserData.builder()
                .id(2)
                .active(true)
                .email("test@med.com")
                .userName("userTest")
                .password("password")
                .roles(Set.of(RoleEntity.builder().id(2).role("PATIENT").build())).build();
    }
    public static UserEntity userDataPatientEntity() {
        return UserEntity.builder()
                .id(2)
                .active(true)
                .email("test@med.com")
                .userName("userTest")
                .password("password")
                .roles(Set.of(RoleEntity.builder().id(2).role("PATIENT").build())).build();
    }
}
