package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
