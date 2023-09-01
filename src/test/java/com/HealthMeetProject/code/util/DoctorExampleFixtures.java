package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ClinicEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DoctorExampleFixtures {

    public static DoctorEntity doctorExample1() {
        return DoctorEntity.builder()
                .clinic(ClinicEntity.builder()
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

    public static DoctorEntity doctorExample2() {
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
                .specialization(Specialization.CARDIOLOGIST)
                .salaryFor15minMeet(BigDecimal.valueOf(150))
                .build();
    }

    public static DoctorEntity doctorExample3() {
        return DoctorEntity.builder()
                .clinic(ClinicEntity.builder()
                        .clinicName("In my house")
                        .country("Germany")
                        .postalCode("23-321")
                        .address("a")
                        .build())
                .name("Grace")
                .surname("Shelby")
                .email("g.shelby@gmail.com")
                .phone("+44 782 323 323")
                .specialization(Specialization.PSYCHIATRIST)
                .salaryFor15minMeet(BigDecimal.valueOf(150))
                .build();
    }


    public static AvailabilityScheduleEntity availabilitySchedule1() {
        return AvailabilityScheduleEntity.builder()
                .since(OffsetDateTime.of(LocalDateTime.of(2025, 5, 1, 8, 30), ZoneOffset.UTC))
                .toWhen(OffsetDateTime.of(LocalDateTime.of(2025, 5, 1, 16, 45), ZoneOffset.UTC))
                .doctor(doctorExample3())
                .build();
    }
    public static AvailabilityScheduleEntity availabilitySchedule2() {
        return AvailabilityScheduleEntity.builder()
                .since(OffsetDateTime.of(LocalDateTime.of(2025, 4, 1, 8, 0), ZoneOffset.UTC))
                .toWhen(OffsetDateTime.of(LocalDateTime.of(2025, 4, 1, 16, 15), ZoneOffset.UTC))
                .doctor(doctorExample3())
                .build();
    }
    public static AvailabilityScheduleEntity availabilitySchedule3() {
        return AvailabilityScheduleEntity.builder()
                .since(OffsetDateTime.of(LocalDateTime.of(2025, 5, 1, 0, 0), ZoneOffset.UTC))
                .toWhen(OffsetDateTime.of(LocalDateTime.of(2025, 5, 1, 23, 59), ZoneOffset.UTC))
                .doctor(doctorExample3())
                .build();
    }

}
