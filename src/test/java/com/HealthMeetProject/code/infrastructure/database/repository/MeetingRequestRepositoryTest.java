package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityRestApiMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeetingRequestRepositoryTest {

    @Mock
    private MeetingRequestJpaRepository meetingRequestJpaRepository;

    @Mock
    private MeetingRequestEntityMapper meetingRequestEntityMapper;

    @Mock
    private MeetingRequestEntityRestApiMapper meetingRequestEntityRestApiMapper;
    @InjectMocks
    private MeetingRequestRepository meetingRequestRepository;

    @Test
    public void testFindAllUpcomingVisits() {
        String email = "patient@example.com";
        List<MeetingRequestEntity> meetingRequestEntities = List.of( MeetingRequestsExampleFixtures.meetingRequestDataEntityExample1());

        when(meetingRequestJpaRepository.findAllUpcomingVisitsByPatient(email)).thenReturn(meetingRequestEntities);
        when(meetingRequestEntityMapper.mapFromEntity(any(MeetingRequestEntity.class))).thenReturn(MeetingRequestsExampleFixtures.meetingRequestDataExample1());

        List<MeetingRequest> result = meetingRequestRepository.findAllUpcomingVisitsByPatient(email);

        assertEquals(meetingRequestEntities.size(), result.size());
    }
    @Test
    public void testFindIfMeetingRequestExistsWithTheSameDateAndDoctor() {
        LocalDateTime since = LocalDateTime.now();
        LocalDateTime toWhen = since.plusHours(1);
        String doctorEmail = "j.kowalski@gmail.com";
        Doctor doctor = DoctorExampleFixtures.doctorExample1();

        when(meetingRequestJpaRepository.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctorEmail))
                .thenReturn(new MeetingRequestEntity());

        boolean result = meetingRequestRepository.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctor);

        assertTrue(result);
    }

    @Test
    public void testFindIfMeetingRequestExistsWithTheSameDateAndDoctor_NotFound() {
        LocalDateTime since = LocalDateTime.now();
        LocalDateTime toWhen = since.plusHours(1);
        String doctorEmail = "j.kowalski@gmail.com";
        Doctor doctor = DoctorExampleFixtures.doctorExample1();

        when(meetingRequestJpaRepository.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctorEmail))
                .thenReturn(null);

        boolean result = meetingRequestRepository.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctor);

        Assertions.assertFalse(result);
    }

    @Test
    public void testDeleteById() {
        Integer meetingId = 1;
        doNothing().when(meetingRequestJpaRepository).deleteById(meetingId);

        assertDoesNotThrow(() -> meetingRequestRepository.deleteById(meetingId));
    }

    @Test
    public void testDeleteById_Exception() {
        Integer meetingId = 1;
        doThrow(new RuntimeException("Error deleting meeting")).when(meetingRequestJpaRepository).deleteById(meetingId);

        assertThrows(RuntimeException.class, () -> meetingRequestRepository.deleteById(meetingId));
    }

    @Test
    public void testFindById() {
        Integer meetingId = 1;
        MeetingRequestEntity meetingRequestEntity = new MeetingRequestEntity();

        when(meetingRequestJpaRepository.findById(meetingId)).thenReturn(Optional.of(meetingRequestEntity));
        when(meetingRequestEntityMapper.mapFromEntity(any(MeetingRequestEntity.class))).thenReturn(new MeetingRequest());

        MeetingRequest result = meetingRequestRepository.findById(meetingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindById_NotFound() {
        Integer meetingId = 1;

        when(meetingRequestJpaRepository.findById(meetingId)).thenReturn(Optional.empty());

        assertThrows(ProcessingException.class, () -> meetingRequestRepository.findById(meetingId));
    }
    @Test
    public void testFindAvailable() {
        //given
        List<MeetingRequestEntity> entityList = new ArrayList<>();
        when(meetingRequestJpaRepository.findAll()).thenReturn(entityList);

       //when
        List<MeetingRequest> result = meetingRequestRepository.findAll();

        //then
        assertEquals(0, result.size());
        verify(meetingRequestJpaRepository, times(1)).findAll();
        verify(meetingRequestEntityMapper, times(entityList.size())).mapFromEntity(any());
    }

    @Test
    public void testFindAllEndedUpVisitsByDoctorAndPatient() {
        //given
        String doctorEmail = "doctor@example.com";
        String patientEmail = "patient@example.com";
        List<MeetingRequestEntity> entityList = new ArrayList<>();
        when(meetingRequestJpaRepository.findAllEndedUpVisits(doctorEmail, patientEmail)).thenReturn(entityList);


       //when
        List<MeetingRequest> result = meetingRequestRepository.findAllEndedUpVisitsByDoctorAndPatient(doctorEmail, patientEmail);

        //then
        assertEquals(0, result.size());
        verify(meetingRequestJpaRepository, times(1)).findAllEndedUpVisits(doctorEmail, patientEmail);
        verify(meetingRequestEntityMapper, times(entityList.size())).mapFromEntity(any());
    }
    @Test
    public void testFindAllUpcomingCompletedVisitsByDoctor() {
        //given
        String doctorEmail = "doctor@example.com";
        List<MeetingRequestEntity> entityList = new ArrayList<>();
        when(meetingRequestJpaRepository.findAllUpcomingCompletedVisitsByDoctor(doctorEmail)).thenReturn(entityList);


       //when
        List<MeetingRequest> result = meetingRequestRepository.findAllUpcomingCompletedVisitsByDoctor(doctorEmail);

        //then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(meetingRequestJpaRepository, times(1)).findAllUpcomingCompletedVisitsByDoctor(doctorEmail);
        verify(meetingRequestEntityMapper, times(entityList.size())).mapFromEntity(any());
    }

    @Test
    public void testFindByPatientEmail() {
        //given
        String patientEmail = "patient@example.com";
        List<MeetingRequestEntity> entityList = new ArrayList<>();
        when(meetingRequestJpaRepository.findAllByPatientEmail(patientEmail)).thenReturn(entityList);


       //when
        List<MeetingRequest> result = meetingRequestRepository.findByPatientEmail(patientEmail);

        //then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(meetingRequestJpaRepository, times(1)).findAllByPatientEmail(patientEmail);
        verify(meetingRequestEntityMapper, times(entityList.size())).mapFromEntity(any());
    }



}
