package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class MeetingRequestEntityRestApiMapperTest {
    @InjectMocks
    private MeetingRequestEntityRestApiMapperImpl mapper;

    @Test
    public void testMapFromEntity() {
        MeetingRequestEntity entity = new MeetingRequestEntity();
        entity.setMeetingId(1);
        entity.setDoctor(DoctorExampleFixtures.doctorEntityExample1());
        entity.setPatient(PatientExampleFixtures.patientEntityExample1());
        MeetingRequest request = mapper.mapFromEntity(entity);

        assertEquals(entity.getMeetingId(), request.getMeetingId());
        assertEquals(entity.getDescription(), request.getDescription());
        assertEquals(entity.getCompletedDateTime(), request.getCompletedDateTime());
        assertEquals(entity.getReceivedDateTime(), request.getReceivedDateTime());
        assertNull(request.getDoctor());
        assertNull(request.getPatient());
    }

    @Test
    public void testMapToEntity() {
        MeetingRequest entity = new MeetingRequest();
        entity.setMeetingId(1);
        entity.setDoctor(DoctorExampleFixtures.doctorExample1());
        entity.setPatient(PatientExampleFixtures.patientExample1());
        MeetingRequestEntity request = mapper.mapToEntity(entity);

        assertEquals(entity.getMeetingId(), request.getMeetingId());
        assertEquals(entity.getDescription(), request.getDescription());
        assertEquals(entity.getCompletedDateTime(), request.getCompletedDateTime());
        assertEquals(entity.getReceivedDateTime(), request.getReceivedDateTime());
        assertEquals(entity.getDoctor().getEmail(), request.getDoctor().getEmail());
        assertEquals(request.getPatient().getEmail(), entity.getDoctor().getEmail());
    }
}
