package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MeetingRequestRepository implements MeetingRequestDAO {
        private final MeetingRequestJpaRepository meetingRequestJpaRepository;
        private final MeetingRequestEntityMapper meetingRequestEntityMapper;
        private final DoctorEntityMapper doctorEntityMapper;
    @Override
    public List<MeetingRequest> findAvailable() {
        return meetingRequestJpaRepository.findAll()
                .stream()
                .map(meetingRequestEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<MeetingRequest> findAllActiveMeetingRequests(String email) {
        return meetingRequestJpaRepository.findAllActiveMeetingRequests(email).stream().
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
    public List<MeetingRequest> findEndedVisits() {
        return meetingRequestJpaRepository.findAllVisitsEndedUp().stream()
                .map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<MeetingRequest> findAllCompletedServiceRequestsByEmail(String email) {
        return meetingRequestJpaRepository.findAllCompletedServiceRequests(email)
                .stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<MeetingRequest> availableServiceRequests(String email) {
        return meetingRequestJpaRepository.availableServiceRequests(email).stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<MeetingRequest> availableEndedVisitsByDoctor(String email) {
        return meetingRequestJpaRepository.availableEndedVisitsByDoctor(email).stream().map(meetingRequestEntityMapper::mapFromEntity).toList();
    }

    @Override
    public boolean findIfMeetingRequestExistsWithTheSameDateAndDoctor(OffsetDateTime since, OffsetDateTime toWhen, Doctor doctor) {
        MeetingRequestEntity meets = meetingRequestJpaRepository.findIfMeetingRequestExistsWithTheSameDateAndDoctor(since, toWhen, doctor.getEmail());
        return Objects.nonNull(meets);
    }
}
