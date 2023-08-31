package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.AddressEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.HealthMeetProject.code.util.DoctorExampleData.doctorExample1;

public class NoteExampleData {
    public static NoteEntity noteExample1() {
        return NoteEntity.builder()
                .description("Dzielny pacjent")

                .illness("Cos z glowa")

                .startTime(OffsetDateTime.of(LocalDateTime.of(2025, 4, 1, 8, 0), ZoneOffset.UTC))

                .endTime(OffsetDateTime.of(LocalDateTime.of(2025, 4, 1, 16, 15), ZoneOffset.UTC)
                )
                .patient(patientExample1())

                .doctor(doctorExample1())

                .build();
    }
    public static PatientEntity patientExample1() {
        return PatientEntity.builder()
                .address(AddressEntity.builder()
                        .city("Warsaw")
                        .country("Poland")
                        .postalCode("96-323")
                        .address("Niewiadomska 13")
                        .build())
                .name("Jan")
                .surname("Kowalski")
                .phone("+48 783 323 323")
                .email("j.kowalski@gmail.com")
                .pesel("3232223212")
                .build();
    }

}
