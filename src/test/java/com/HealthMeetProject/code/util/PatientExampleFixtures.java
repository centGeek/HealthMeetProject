package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.AddressEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;

public class PatientExampleFixtures {
    public static PatientEntity patientExample1() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
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

    public static PatientEntity patientExample2() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
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

    public static PatientEntity patientExample3() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
                        .city("London")
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
//    public static UserEntity userEntityExample1(){
//        return UserEntity.builder()
//                .userName("j_kowalski")
//                .email("j.kowalski@gmail.com")
//                .password("test")
//                .active(true)
//                .build();
//    } public static UserEntity userEntityExample2(){
//        return UserEntity.builder()
//                .userName("t_shelby")
//                .email("t.shelby@gmail.com")
//                .password("test")
//                .active(true)
//                .build();
//    }
//    public static UserEntity userEntityExample3(){
//        return UserEntity.builder()
//                .userName("g_shelby")
//                .email("g.shelby@gmail.com")
//                .password("test")
//                .active(true)
//                .build();
//    }
}