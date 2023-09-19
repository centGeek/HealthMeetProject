package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ClinicEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DoctorExampleFixtures {

    public static Doctor doctorExample1() {
        return Doctor.builder()
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
                .earningsPerVisit(BigDecimal.ONE)
                .build();
    }
    public static Doctor doctorExample3() {
        return Doctor.builder()
                .clinic(Clinic.builder()
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
                .earningsPerVisit(BigDecimal.valueOf(150))
                .build();
    }
    public static DoctorDTO doctorDTOExample3() {
        return DoctorDTO.builder()
                .clinic(Clinic.builder()
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
                .earningsPerVisit(BigDecimal.valueOf(150))
                .build();
    }
    public static DoctorEntity doctorEntityExample1() {
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
                .earningsPerVisit(BigDecimal.ONE)
                .build();
    }


    public static DoctorEntity doctorEntityExample2() {
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
                .earningsPerVisit(BigDecimal.valueOf(150))
                .build();
    }

    public static DoctorEntity doctorEntityExample3() {
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
                .earningsPerVisit(BigDecimal.valueOf(150))
                .build();
    }



        public static AvailabilityScheduleDTO   availabilityScheduleDTO1() {
            return AvailabilityScheduleDTO.builder()
                    .since(LocalDateTime.of(2025, 5, 1, 8, 30))
                    .toWhen(LocalDateTime.of(2025, 5, 1, 16, 45))
                    .doctor(doctorDTOExample3())
                    .availableTerm(true)
                    .availableDay(true)
                    .build();
        }
        public static AvailabilitySchedule availabilitySchedule1() {
            return AvailabilitySchedule.builder()
                    .since(LocalDateTime.of(2025, 5, 1, 8, 30))
                    .toWhen(LocalDateTime.of(2025, 5, 1, 16, 45))
                    .doctor(doctorExample3())
                    .availableTerm(true)
                    .availableDay(true)
                    .build();
        }
    public static AvailabilityScheduleDTO availabilityScheduleDTO2() {
        return AvailabilityScheduleDTO.builder()
                .since(LocalDateTime.of(2025, 4, 1, 8, 0))
                .toWhen(LocalDateTime.of(2025, 4, 1, 16, 15))
                .doctor(doctorDTOExample3())
                .availableTerm(true)
                .availableDay(true)
                .build();
    }
    public static AvailabilityScheduleDTO availabilityScheduleDTO3() {
        return AvailabilityScheduleDTO.builder()
                .since(LocalDateTime.of(2025, 5, 1, 0, 0))
                .toWhen(LocalDateTime.of(2025, 5, 1, 23, 59))
                .doctor(doctorDTOExample3())
                .availableTerm(true)
                .availableDay(true)
                .build();
    }
    public static AvailabilityScheduleEntity availabilityScheduleEntity1() {
        return AvailabilityScheduleEntity.builder()
                .since(LocalDateTime.of(2025, 5, 1, 8, 30))
                .toWhen(LocalDateTime.of(2025, 5, 1, 16, 45))
                .doctor(doctorEntityExample3())
                .availableTerm(true)
                .availableDay(true)
                .build();
    }
    public static AvailabilityScheduleEntity availabilityScheduleEntity2() {
        return AvailabilityScheduleEntity.builder()
                .since(LocalDateTime.of(2025, 4, 1, 8, 0))
                .toWhen(LocalDateTime.of(2025, 4, 1, 16, 15))
                .availableTerm(true)
                .availableDay(true)
                .doctor(doctorEntityExample3())
                .build();
    }

    public static AvailabilityScheduleEntity availabilityScheduleEntity3() {
        return AvailabilityScheduleEntity.builder()
                .since(LocalDateTime.of(2025, 5, 1, 0, 0))
                .toWhen(LocalDateTime.of(2025, 5, 1, 23, 59))
                .availableTerm(true)
                .availableDay(true)
                .doctor(doctorEntityExample3())
                .build();
    }

}
