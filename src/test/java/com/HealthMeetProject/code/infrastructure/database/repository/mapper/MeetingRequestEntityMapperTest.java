package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
        MeetingRequest meetingRequest = MeetingRequest.builder()
                .meetingId(1)
                .completedDateTime(OffsetDateTime.of(LocalDateTime.of(2023, 3, 2, 10, 0), ZoneOffset.UTC))
                .receivedDateTime(OffsetDateTime.of(LocalDateTime.of(2023, 3, 1, 10, 0), ZoneOffset.UTC))
                .visitStart(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 0), ZoneOffset.UTC))
                .visitEnd(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 15), ZoneOffset.UTC))
                .description("milego dnia")
                .patient(PatientExampleFixtures.patientExample1())
                .doctor(DoctorExampleFixtures.doctorExample1())
                .build();
        //when
        MeetingRequestEntity meetingRequestEntity = mapper.mapToEntity(meetingRequest);

        // then
        assertions(meetingRequestEntity,meetingRequest);
    }

    @Test
    public void shouldMapMeetingRequestEntityToMeetingRequest() {
        // given
        MeetingRequestEntity meetingRequestEntity = MeetingRequestEntity.builder()
                .meetingId(1)
                .completedDateTime(OffsetDateTime.of(LocalDateTime.of(2023, 3, 2, 10, 0), ZoneOffset.UTC))
                .receivedDateTime(OffsetDateTime.of(LocalDateTime.of(2023, 3, 1, 10, 0), ZoneOffset.UTC))
                .visitStart(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 0), ZoneOffset.UTC))
                .visitEnd(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 15), ZoneOffset.UTC))
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