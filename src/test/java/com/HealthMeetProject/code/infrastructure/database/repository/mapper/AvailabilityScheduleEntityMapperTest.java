package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class AvailabilityScheduleEntityMapperTest {

    private AvailabilityScheduleEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new AvailabilityScheduleEntityMapperImpl();
    }

    @Test
    public void shouldMapFromEntity() {
        //given
        AvailabilityScheduleEntity entity = new AvailabilityScheduleEntity();
        entity.setAvailability_schedule_id(1);
        entity.setAvailableTerm(true);
        entity.setAvailableDay(true);
        entity.setDoctor(DoctorExampleFixtures.doctorEntityExample1());
        entity.setSince(LocalDateTime.of(2025, 2, 12, 14, 20));
        entity.setToWhen(LocalDateTime.of(2025, 2, 12, 20, 20));
        //when
        AvailabilitySchedule schedule = mapper.mapFromEntity(entity);
        //then
        assertEquals(entity, schedule);
    }

    private static void assertEquals(AvailabilityScheduleEntity entity, AvailabilitySchedule schedule) {
        Assertions.assertEquals(entity.getAvailability_schedule_id(), schedule.getAvailability_schedule_id());
        Assertions.assertEquals(entity.isAvailableTerm(), schedule.isAvailableTerm());
        Assertions.assertEquals(entity.isAvailableDay(), schedule.isAvailableDay());
        Assertions.assertEquals(entity.getDoctor().getName(), schedule.getDoctor().getName());
        Assertions.assertEquals(entity.getDoctor().getSurname(), schedule.getDoctor().getSurname());
        Assertions.assertEquals(entity.getDoctor().getSpecialization(), schedule.getDoctor().getSpecialization());
        Assertions.assertEquals(entity.getDoctor().getEmail(), schedule.getDoctor().getEmail());
        Assertions.assertEquals(entity.getDoctor().getPhone(), schedule.getDoctor().getPhone());
        Assertions.assertEquals(entity.getDoctor().getEarningsPerVisit(), schedule.getDoctor().getEarningsPerVisit());
        Assertions.assertNull(entity.getDoctor().getUser());
    }

    @Test
    public void shouldMapToEntity() {
        //given
        AvailabilitySchedule schedule = new AvailabilitySchedule();
        schedule.setAvailability_schedule_id(5);
        schedule.setAvailableTerm(false);
        schedule.setAvailableDay(false);
        schedule.setDoctor(DoctorExampleFixtures.doctorExample1());
        schedule.setSince(LocalDateTime.of(2025, 3, 12, 14, 20));
        schedule.setToWhen(LocalDateTime.of(2025, 3, 12, 20, 20));
        //when
        AvailabilityScheduleEntity entity = mapper.mapToEntity(schedule);
        //then
        assertEquals(entity, schedule);
    }
}
