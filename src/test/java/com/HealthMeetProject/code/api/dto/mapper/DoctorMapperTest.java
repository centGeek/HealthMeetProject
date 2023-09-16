package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorMapperTest {
    private DoctorMapper doctorMapper;

    @BeforeEach
    public void setUp() {
        doctorMapper = Mappers.getMapper(DoctorMapper.class);
    }

    @Test
    public void shouldMapDoctorToDoctorDTO() {
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
        DoctorDTO doctorDTO = doctorMapper.mapToDTO(doctor);

        // then
        assertions(doctorDTO, doctor);
    }

    @Test
    public void shouldMapDoctorDTODTOToDoctorDTO() {
        // given
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1);
        doctorDTO.setName("Ibuprofen");
        doctorDTO.setSpecialization(Specialization.ENDOCRINOLOGIST);
        doctorDTO.setEarningsPerVisit(BigDecimal.TEN);
        doctorDTO.setClinic(Clinic.builder()
                .country("Poland")
                .clinicName("ClinicMedic")
                .postalCode("96-343")
                .address("streetowska")
                .build());
        //when
        Doctor doctor = doctorMapper.mapFromDTO(doctorDTO);
        //then
        assertions(doctorDTO, doctor);
    }

    private static void assertions(DoctorDTO doctorDTO, Doctor doctor) {
        assertEquals(doctorDTO.getDoctorId(), doctor.getDoctorId());
        assertEquals(doctorDTO.getName(), doctor.getName());
        assertEquals(doctorDTO.getPhone(), doctor.getPhone());
        assertEquals(doctorDTO.getEmail(), doctor.getEmail());
        assertEquals(doctorDTO.getEarningsPerVisit(), doctor.getEarningsPerVisit());
        assertEquals(doctorDTO.getClinic().getClinicName(), doctor.getClinic().getClinicName());
        assertEquals(doctorDTO.getClinic().getCountry(), doctor.getClinic().getCountry());
        assertEquals(doctorDTO.getClinic().getAddress(), doctor.getClinic().getAddress());
        assertEquals(doctorDTO.getClinic().getPostalCode(), doctor.getClinic().getPostalCode());
    }
}