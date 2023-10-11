package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.api.dto.mapper.UserEntityMapper;
import com.HealthMeetProject.code.domain.*;
import com.HealthMeetProject.code.infrastructure.database.entity.*;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorEntityMapperTest {
    private DoctorEntityMapper mapper;
    private MeetingRequestEntityMapper meetingRequestEntityMapper;
    private UserEntityMapper userEntityMapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(DoctorEntityMapper.class);
        meetingRequestEntityMapper = Mappers.getMapper(MeetingRequestEntityMapper.class);
        userEntityMapper = Mappers.getMapper(UserEntityMapper.class);
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
        MeetingRequestEntity meetingRequestEntity = new MeetingRequestEntity();
        meetingRequestEntity.setDoctor(doctorEntity);
        PatientEntity patientEntity = PatientExampleFixtures.patientEntityExample1();
        AddressEntity address = new AddressEntity();
        address.setAddressId(3);
        address.setCity("city");
        address.setCountry("country");
        address.setPostalCode("94-323");
        address.setAddress("address");
        patientEntity.setAddress(address);
        meetingRequestEntity.setPatient(patientEntity);
        meetingRequestEntity.setMeetingRequestNumber("32222323212");
        meetingRequestEntity.setDescription("helo");
        meetingRequestEntity.setVisitStart(LocalDateTime.MAX);
        meetingRequestEntity.setVisitEnd(LocalDateTime.MIN);
        meetingRequestEntity.setCompletedDateTime(LocalDateTime.MIN);
        meetingRequestEntity.setReceivedDateTime(LocalDateTime.MIN);
        meetingRequestEntity.setDoctor(null);
        MeetingRequest meetingRequest = meetingRequestEntityMapper.mapFromEntity(meetingRequestEntity);
        MeetingRequestEntity meetingRequestEntity1 = meetingRequestEntityMapper.mapToEntity(meetingRequest);


        UserData user = new UserData();
        user.setUserName("kaszka_manna");
        user.setActive(true);
        user.setEmail("kaszka_manna@kaszanka.pl");
        user.setPassword("klucha_w_galarecie");
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        doctorEntity.setUser(userEntity);

        //when
        //then
        Assertions.assertEquals(meetingRequestEntity1.getDescription(), meetingRequestEntity.getDescription());
        Assertions.assertEquals(meetingRequestEntity1.getMeetingId(), meetingRequestEntity.getMeetingId());
        Assertions.assertEquals(meetingRequestEntity1.getVisitEnd(), meetingRequestEntity.getVisitEnd());
        Assertions.assertEquals(meetingRequestEntity1.getVisitStart(), meetingRequestEntity.getVisitStart());
        Assertions.assertEquals(meetingRequestEntity1.getCompletedDateTime(), meetingRequestEntity.getCompletedDateTime());
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