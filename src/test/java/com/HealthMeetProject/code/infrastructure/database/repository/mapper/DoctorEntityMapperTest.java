package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.ClinicEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorEntityMapperTest {
    private DoctorEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(DoctorEntityMapper.class);
    }

    @Test
    public void shouldMapDoctorToDoctorEntity() {
        // given
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setName("Ibuprofen");
        doctor.setSpecialization(Specialization.ENDOCRINOLOGIST);
        doctor.setEarningsPerVisit(BigDecimal.TEN);
        doctor.setClinic(Clinic.builder()
                .country("Poland")
                .clinicName("ClinicMedic")
                .postalCode("96-343")
                .address("streetowska")
                .build());
        //when
        DoctorEntity doctorEntity = mapper.mapToEntity(doctor);

        // then
        assertions(doctorEntity, doctor);
    }

    @Test
    public void shouldMapDoctorEntityEntityToDoctorEntity() {
        // given
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(1);
        doctorEntity.setName("Ibuprofen");
        doctorEntity.setSpecialization(Specialization.ENDOCRINOLOGIST);
        doctorEntity.setEarningsPerVisit(BigDecimal.TEN);
        doctorEntity.setClinic(ClinicEntity.builder()
                .country("Poland")
                .clinicName("ClinicMedic")
                .postalCode("96-343")
                .address("streetowska")
                .build());
        //when
        Doctor doctor = mapper.mapFromEntity(doctorEntity);
        //then
        assertions(doctorEntity, doctor);
    }

    private static void assertions(DoctorEntity doctorEntity, Doctor doctor) {
        assertEquals(doctorEntity.getDoctorId(), doctor.getDoctorId());
        assertEquals(doctorEntity.getName(), doctor.getName());
        assertEquals(doctorEntity.getPhone(), doctor.getPhone());
        assertEquals(doctorEntity.getEmail(), doctor.getEmail());
        assertEquals(doctorEntity.getEarningsPerVisit(), doctor.getEarningsPerVisit());
        assertEquals(doctorEntity.getClinic().getClinicName(), doctor.getClinic().getClinicName());
        assertEquals(doctorEntity.getClinic().getCountry(), doctor.getClinic().getCountry());
        assertEquals(doctorEntity.getClinic().getAddress(), doctor.getClinic().getAddress());
        assertEquals(doctorEntity.getClinic().getPostalCode(), doctor.getClinic().getPostalCode());
    }
}