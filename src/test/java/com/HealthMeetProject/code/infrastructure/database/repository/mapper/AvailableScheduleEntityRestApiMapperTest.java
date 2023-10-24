package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class AvailableScheduleEntityRestApiMapperTest {

    @InjectMocks
    private AvailableScheduleEntityRestApiMapperImpl mapper;

    @Test
    public void testMapFromEntity() {
        AvailabilityScheduleEntity entity = new AvailabilityScheduleEntity();
        entity.setAvailability_schedule_id(1);
        entity.setDoctor(DoctorExampleFixtures.doctorEntityExample1());
        entity.setSince(LocalDateTime.now());
        entity.setToWhen(LocalDateTime.now());
        entity.setAvailableDay(true);

        AvailabilitySchedule schedule = mapper.mapFromEntity(entity);

        assertEquals(entity.getAvailability_schedule_id(), schedule.getAvailability_schedule_id());
        assertEquals(entity.getToWhen(), schedule.getToWhen());
        assertEquals(entity.getSince(), schedule.getSince());
        assertNull(schedule.getDoctor());
    }
}
