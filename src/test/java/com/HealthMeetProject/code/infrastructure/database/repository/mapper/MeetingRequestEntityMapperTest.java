package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Clinic;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeetingRequestEntityMapperTest {
    private MeetingRequestEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(MeetingRequestEntityMapper.class);
    }

    @Test
    public void shouldMapMeetingRequestToMeetingRequestEntity() {
        // given
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        Clinic clinic = new Clinic();
        clinic.setClinicName("prosto");
        clinic.setCountry("Poland");
        clinic.setPostalCode("96-021");
        clinic.setAddress("zielona 2");
        doctor.setUser(DoctorDTOFixtures.getUserDTO1());
        doctor.setClinic(clinic);
        MeetingRequest meetingRequest = MeetingRequest.builder()
                .meetingId(1)
                .completedDateTime(LocalDateTime.of(2023, 3, 2, 10, 0))
                .receivedDateTime(LocalDateTime.of(2023, 3, 1, 10, 0))
                .visitStart(LocalDateTime.of(2020, 3, 2, 10, 0))
                .visitEnd(LocalDateTime.of(2020, 3, 2, 10, 15))
                .description("milego dnia")
                .patient(PatientExampleFixtures.patientExample1())
                .doctor(doctor)
                .build();
        //when
        MeetingRequestEntity meetingRequestEntity = mapper.mapToEntity(meetingRequest);

        // then
        assertions(meetingRequestEntity, meetingRequest);
    }

    @Test
    public void shouldMapMeetingRequestEntityToMeetingRequest() {
        // given
        MeetingRequestEntity meetingRequestEntity = MeetingRequestEntity.builder()
                .meetingId(1)
                .completedDateTime(LocalDateTime.of(2023, 3, 2, 10, 0))
                .receivedDateTime(LocalDateTime.of(2023, 3, 1, 10, 0))
                .visitStart(LocalDateTime.of(2020, 3, 2, 10, 0))
                .visitEnd(LocalDateTime.of(2020, 3, 2, 10, 15))
                .description("milego dnia")
                .patient(PatientExampleFixtures.patientEntityExample1())
                .doctor(DoctorExampleFixtures.doctorEntityExample1())
                .build();
        //when
        MeetingRequest meetingRequest = mapper.mapFromEntity(meetingRequestEntity);
        //then
        assertions(meetingRequestEntity, meetingRequest);
    }

    private static void assertions(MeetingRequestEntity meetingRequestEntity, MeetingRequest meetingRequest) {
        assertEquals(meetingRequestEntity.getMeetingId(), meetingRequest.getMeetingId());
        assertEquals(meetingRequestEntity.getDescription(), meetingRequest.getDescription());
        assertEquals(meetingRequestEntity.getVisitStart(), meetingRequest.getVisitStart());
        assertEquals(meetingRequestEntity.getVisitEnd(), meetingRequest.getVisitEnd());
        assertEquals(meetingRequestEntity.getCompletedDateTime(), meetingRequest.getCompletedDateTime());
        assertEquals(meetingRequestEntity.getReceivedDateTime(), meetingRequest.getReceivedDateTime());
        assertEquals(meetingRequestEntity.getPatient().getEmail(), meetingRequest.getPatient().getEmail());
    }
}