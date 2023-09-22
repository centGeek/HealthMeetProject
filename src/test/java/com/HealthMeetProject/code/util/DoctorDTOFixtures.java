package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.ClinicEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
@AllArgsConstructor
public class DoctorDTOFixtures {


    public static DoctorDTO getDoctorDTO1() {
        return DoctorDTO.builder()
                .name("Jan")
                .clinic(getClinicDTOToRegister())
                .surname("Kowalski")
                .email("j.kowalski@gmail.com")
                .specialization(Specialization.PULMONOLOGIST)
                .phone("+48 724 483 007")
                .earningsPerVisit(BigDecimal.valueOf(50))
                .user(getUserDTO1())
                .build();
    }
    public static DoctorDTO getDoctorDTO2() {
        return DoctorDTO.builder()
                .name("Krzysiek")
                .clinic(getClinicDTOToRegister())
                .surname("Stodołski")
                .email("k.stodoła@gmail.com")
                .specialization(Specialization.ENDOCRINOLOGIST)
                .phone("+48 589 483 007")
                .earningsPerVisit(BigDecimal.valueOf(12))
                .user(getUserDTO2())
                .build();
    }
    public static UserData userDataDoctor() {
        return UserData.builder()
                .id(1)
                .active(true)
                .email("test@gmail.com")
                .userName("usertest")
                .password("test")
                .roles(Set.of(RoleEntity.builder().id(1).role("DOCTOR").build())).build();
    }
    public static Clinic getClinicDTOToRegister() {
        return Clinic.builder()
                .clinicName("Medi Clinic")
                .country("Poland")
                .address("ul. Szkolna 3")
                .postalCode("90-321")
                .build();
    }

    private static UserData getUserDTO1() {
        return UserData.builder()
                .active(true)
                .userName("j_kowalski")
                .password("test")
                .roles(Set.of(new RoleEntity(3, "DOCTOR")))
                .email("j.kowalski@gmail.com")
                .build();
    }
    private static UserData getUserDTO2() {
        return UserData.builder()
                .active(true)
                .userName("k_stodola")
                .password("test")
                .roles(Set.of(new RoleEntity(3, "DOCTOR")))
                .email("k.stodola@gmail.com")
                .build();
    }

    public static DoctorEntity getDoctorEntityToRegister() {
        return DoctorEntity.builder()
                .name("Jan")
                .clinic(getClinicEntityToRegister())
                .surname("Tomaszewski")
                .email("j.Tomasz@gmail.com")
                .specialization(Specialization.PULMONOLOGIST)
                .phone("+48 724 483 007")
                .earningsPerVisit(BigDecimal.valueOf(50))
                .user(getUserEntityToRegister())
                .build();
    }

    public static ClinicEntity getClinicEntityToRegister() {
        return ClinicEntity.builder()
                .clinicName("Medi Clinic")
                .country("Poland")
                .address("ul. Szkolna 3")
                .postalCode("90-321")
                .build();
    }

    private static UserEntity getUserEntityToRegister() {
        return UserEntity.builder()
                .active(true)
                .userName("j_tomasz")
                .password("test")
                .roles(Set.of(new RoleEntity(4, "DOCTOR")))
                .email("jtomasz@gmail.com")
                .build();
    }

}
