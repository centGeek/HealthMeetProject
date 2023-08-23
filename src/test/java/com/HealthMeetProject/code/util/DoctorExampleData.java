package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.ClinicEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.Specialization;

import java.math.BigDecimal;

public class DoctorExampleData {

    public static DoctorEntity doctorExample1(){
      return DoctorEntity.builder()
                .clinic(ClinicEntity.builder()
                        .clinicName("HealthMeet")
                        .country("Poland")
                        .postalCode("96-323")
                        .address("Niewiadomska 13")
                        .build())
                .name("Jan")
                .surname("Kowalski")
                .email("j.kowalski@gmail.com")
                .phone("+48 783 323 323")
                .specialization(Specialization.CARDIOLOGIST)
                .salaryFor15minMeet(BigDecimal.ONE)
                .build();
    }
    public static DoctorEntity doctorExample2(){
      return DoctorEntity.builder()
                .clinic(ClinicEntity.builder()
                        .clinicName("Luxmed")
                        .country("England")
                        .postalCode("323-321")
                        .address("English 13")
                        .build())
                .name("Tommy")
                .surname("Shelby")
                .email("t.shelby@gmail.com")
                .phone("+44 782 323 323")
                .specialization(Specialization.PSYCHIATRIST)
              .salaryFor15minMeet(BigDecimal.valueOf(150))
                .build();
    }
}
