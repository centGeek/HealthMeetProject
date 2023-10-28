package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvailabilityScheduleRepositoryTest {

    @Mock
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;

    @Mock
    private AvailabilityScheduleMapper availabilityScheduleMapper;

    @Mock
    private AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    @InjectMocks
    private AvailabilityScheduleRepository availabilityScheduleRepository;


    @Test
    void testFindAllTermsByGivenDoctor() {
        // given
        String doctorEmail = "doctor@example.com";
        Set<AvailabilityScheduleEntity> entities = new HashSet<>();
        when(availabilityScheduleJpaRepository.findAllTermsByGivenDoctor(doctorEmail)).thenReturn(entities);

        //when
        List<AvailabilitySchedule> schedules = availabilityScheduleRepository.findAllTermsByGivenDoctor(doctorEmail);

        // then
        assertEquals(0, schedules.size());
    }

    @Test
    void testAddTerm() {
        // given
        LocalDateTime since = LocalDateTime.now();
        LocalDateTime toWhen = since.plusHours(2);
        AvailabilityScheduleEntity scheduleEntity = DoctorExampleFixtures.availabilityScheduleEntity1();
        AvailabilitySchedule schedule = DoctorExampleFixtures.availabilitySchedule1();

        when(availabilityScheduleEntityMapper.mapFromEntity(any())).thenReturn(schedule);

        when(availabilityScheduleJpaRepository.saveAndFlush(any())).thenReturn(scheduleEntity);

        // when
        AvailabilitySchedule scheduleResult = availabilityScheduleRepository.addTerm(since, toWhen, DoctorExampleFixtures.doctorEntityExample3());

        // then
        Assertions.assertNotNull(scheduleResult);
    }

    @Test
    void testFindAllAvailableTermsByGivenDoctor() {
        // given
        String doctorEmail = "doctor@example.com";
        List<AvailabilityScheduleEntity> entities = new ArrayList<>();
        when(availabilityScheduleJpaRepository.findAllAvailableTermsByGivenDoctor(doctorEmail)).thenReturn(entities);

        //when
        List<AvailabilitySchedule> schedules = availabilityScheduleRepository.findAllAvailableTermsByGivenDoctor(doctorEmail);

        // then
        assertEquals(0, schedules.size());
    }

    @Test
    void testFindById() {
        // given
        Integer id = 1;
        AvailabilityScheduleEntity entity = new AvailabilityScheduleEntity();
        when(availabilityScheduleJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(availabilityScheduleEntityMapper.mapFromEntity(entity)).thenReturn(new AvailabilitySchedule());

        //when
        AvailabilitySchedule schedule = availabilityScheduleRepository.findById(id);

        // then
        assertNotNull(schedule);
    }

    @Test
    void testDeleteById() {
        // given
        Integer id = 1;

        //when
        availabilityScheduleRepository.deleteById(id);

        // then
        verify(availabilityScheduleJpaRepository, times(1)).deleteById(id);
    }

    @Test
    void testSave() {
        // given
        AvailabilityScheduleEntity entity = new AvailabilityScheduleEntity();

        //when
        availabilityScheduleRepository.save(entity);

        // then
        verify(availabilityScheduleJpaRepository, times(1)).save(entity);
    }
}
