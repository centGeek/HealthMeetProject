package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.domain.Address;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.AddressEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.HealthMeetProject.code.util.DoctorExampleFixtures.doctorEntityExample1;

public class NoteExampleFixtures {
    public static NoteEntity noteEntityExample1() {
        return NoteEntity.builder()
                .description("Dzielny pacjent")

                .illness("Cos z glowa")

                .startTime(LocalDateTime.of(2025, 4, 1, 8, 0))

                .endTime(LocalDateTime.of(2025, 4, 1, 16, 15))
                .patient(patientEntityExample1())

                .doctor(doctorEntityExample1())

                .build();
    }

    public static Note noteExample1() {
        return Note.builder()
                .description("Dzielny pacjent")
                .illness("Cos z glowa")
                .startTime(LocalDateTime.of(2025, 4, 1, 8, 0))
                .endTime(LocalDateTime.of(2025, 4, 1, 16, 15))
                .patient(patientExample1())
                .doctor(DoctorExampleFixtures.doctorExample1())
                .build();
    }

    public static PatientEntity patientEntityExample1() {
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

    public static Patient patientExample1() {
        return Patient.builder()
                .address(Address.builder()
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
