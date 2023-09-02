package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class AvailabilityScheduleRepository implements AvailabilityScheduleDAO {
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private final AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;


    @Override
    public List<AvailabilitySchedule> findAllTermsByGivenDoctor(String email) {
        return availabilityScheduleJpaRepository.findAllTermsByGivenDoctor(email)
                .stream().map(availabilityScheduleEntityMapper::map).toList();
    }

    @Override
    public AvailabilitySchedule addTerm(OffsetDateTime since, OffsetDateTime toWhen, DoctorEntity doctor) {
        AvailabilityScheduleEntity schedule = AvailabilityScheduleEntity.builder().
                since(since)
                .toWhen(toWhen)
                .doctor(doctor)
                .build();
        availabilityScheduleJpaRepository.saveAndFlush(schedule);
        AvailabilitySchedule availabilitySchedule = availabilityScheduleEntityMapper.map(schedule);
        return availabilitySchedule;
    }
}
