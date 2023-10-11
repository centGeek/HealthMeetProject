package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.api.dto.mapper.UserEntityMapper;
import com.HealthMeetProject.code.api.dto.mapper.UserEntityMapperImpl;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class AvailabilityScheduleEntityMapperTest {

    private AvailabilityScheduleEntityMapper availableScheduleEntityMapper;


    @BeforeEach
    public void setUp() {

        availableScheduleEntityMapper = new AvailabilityScheduleEntityMapperImpl();
    }

    @Test
    public void shouldMapFromEntity() {
        //given
        AvailabilityScheduleEntity entity = new AvailabilityScheduleEntity();
        entity.setAvailability_schedule_id(1);
        entity.setAvailableTerm(true);
        entity.setAvailableDay(true);
        entity.setSince(LocalDateTime.of(2025, 2, 12, 14, 20));
        entity.setToWhen(LocalDateTime.of(2025, 2, 12, 20, 20));
        //when
        AvailabilitySchedule schedule = availableScheduleEntityMapper.mapFromEntity(entity);
        //then
        assertEquals(entity, schedule);
    }

    private static void assertEquals(AvailabilityScheduleEntity entity, AvailabilitySchedule schedule) {
        Assertions.assertEquals(entity.getAvailability_schedule_id(), schedule.getAvailability_schedule_id());
        Assertions.assertEquals(entity.isAvailableTerm(), schedule.isAvailableTerm());
        Assertions.assertEquals(entity.isAvailableDay(), schedule.isAvailableDay());
    }

    @Test
    public void shouldMapToEntity() {
        //given
        AvailabilitySchedule schedule = new AvailabilitySchedule();
        schedule.setAvailability_schedule_id(5);
        schedule.setAvailableTerm(false);
        schedule.setAvailableDay(false);
        schedule.setSince(LocalDateTime.of(2025, 3, 12, 14, 20));
        schedule.setToWhen(LocalDateTime.of(2025, 3, 12, 20, 20));
        //when
        AvailabilityScheduleEntity entity = availableScheduleEntityMapper.mapToEntity(schedule);
        //then
        assertEquals(entity, schedule);
    }





}
