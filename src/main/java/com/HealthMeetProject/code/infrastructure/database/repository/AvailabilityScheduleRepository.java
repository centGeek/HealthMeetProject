package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailableScheduleEntityRestApiMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class AvailabilityScheduleRepository implements AvailabilityScheduleDAO {
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private final AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    private final AvailableScheduleEntityRestApiMapper availableScheduleEntityRestApiMapper;


    @Override
    public List<AvailabilitySchedule> findAllTermsByGivenDoctor(String email) {
        return availabilityScheduleJpaRepository.findAllTermsByGivenDoctor(email)
                .stream().map(availabilityScheduleEntityMapper::mapFromEntity).toList();
    }

    @Override
    public AvailabilitySchedule addTerm(LocalDateTime since, LocalDateTime toWhen, DoctorEntity doctor) {
        AvailabilityScheduleEntity schedule = AvailabilityScheduleEntity.builder()
                .since(since)
                .toWhen(toWhen)
                .doctor(doctor)
                .availableDay(true)
                .availableTerm(true)
                .build();
        availabilityScheduleJpaRepository.saveAndFlush(schedule);
        return availabilityScheduleEntityMapper.mapFromEntity(schedule);
    }

    @Override
    public List<AvailabilitySchedule> findAllAvailableTermsByGivenDoctor(String email) {
        return availabilityScheduleJpaRepository.findAllAvailableTermsByGivenDoctor(email).stream().map(availabilityScheduleEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<AvailabilitySchedule> restFindAllAvailableTermsByGivenDoctor(String email) {
        return availabilityScheduleJpaRepository.findAllAvailableTermsByGivenDoctor(email).stream()
                .map(availableScheduleEntityRestApiMapper::mapFromEntity).toList();
    }

    @Override
    public AvailabilitySchedule findById(Integer id) {
        return availabilityScheduleEntityMapper.mapFromEntity(availabilityScheduleJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can not find resource")));
    }

    @Override
    public void deleteById(Integer availabilityScheduleId) {
        availabilityScheduleJpaRepository.deleteById(availabilityScheduleId);
    }

    @Override
    public void save(AvailabilityScheduleEntity availabilityScheduleEntity) {
        availabilityScheduleJpaRepository.save(availabilityScheduleEntity);
    }

    @Override
    public List<AvailabilityScheduleDTO> findAll() {
        return availabilityScheduleJpaRepository.findAll().stream()
                .map(availabilityScheduleEntityMapper::mapFromEntity)
                .map(availabilityScheduleMapper::mapToDTO).toList();
    }

    @Override
    public List<AvailabilityScheduleDTO> restFindAll() {
        return availabilityScheduleJpaRepository.findAll().stream()
                .map(availableScheduleEntityRestApiMapper::mapFromEntity)
                .map(availabilityScheduleMapper::mapToDTO).toList();
    }
}
