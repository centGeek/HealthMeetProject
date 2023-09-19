package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class MeetingRequestRepository implements MeetingRequestDAO {
        private final MeetingRequestJpaRepository meetingRequestJpaRepository;
        private final MeetingRequestEntityMapper meetingRequestEntityMapper;

    @Override
    public List<MeetingRequest> findAvailable() {
        return meetingRequestJpaRepository.findAll()
                .stream()
                .map(meetingRequestEntityMapper::mapFromEntity)
                .toList();
    }
    @Override
    public List<MeetingRequest> findAllUpcomingVisitsByPatient(String email){
        return meetingRequestJpaRepository.findAllUpcomingVisitsByPatient(email).stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }
    public List<MeetingRequest> findAllUpcomingCompletedVisitsByDoctor(String email){
        return meetingRequestJpaRepository.findAllUpcomingCompletedVisitsByDoctor(email).stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<MeetingRequest> findAllEndedUpVisitsByDoctorAndPatient(String doctorEmail, String patientEmail) {
        return meetingRequestJpaRepository.findAllEndedUpVisits(doctorEmail, patientEmail).stream()
                .map(meetingRequestEntityMapper::mapFromEntity).toList();
    }


    @Override
    public List<MeetingRequest> findAllActiveMeetingRequests(String email) {
        return meetingRequestJpaRepository.findAllActiveMeetingRequestsByPatient(email).stream().
                map(meetingRequestEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<MeetingRequest> findByPatientEmail(String email) {
           return meetingRequestJpaRepository.findAllByPatientEmail(email).stream().
                map(meetingRequestEntityMapper::mapFromEntity)
                .toList();
    }


    @Override
    public List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email) {
        return meetingRequestJpaRepository.findAllCompletedMeetingRequestsByPatient(email)
                .stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<MeetingRequest> availableServiceRequests(String email) {
        return meetingRequestJpaRepository.findAllActiveMeetingRequestsByDoctor(email).stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<MeetingRequest> availableEndedVisitsByDoctor(String email) {
        return meetingRequestJpaRepository.completedMeetingRequestsByDoctor(email).stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public boolean findIfMeetingRequestExistsWithTheSameDateAndDoctor(LocalDateTime since, LocalDateTime toWhen, Doctor doctor) {
        MeetingRequestEntity meets = meetingRequestJpaRepository.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctor.getEmail());
        return Objects.nonNull(meets);
    }

    @Override
    public void deleteById(Integer meetingId) {
        meetingRequestJpaRepository.deleteById(meetingId);
    }

    @Override
    public MeetingRequest findById(Integer meetingId) {
        return meetingRequestEntityMapper.mapFromEntity(meetingRequestJpaRepository.findById(
                meetingId).orElseThrow(() -> new ProcessingException("Could not find meeting request by this id")));
    }
}
