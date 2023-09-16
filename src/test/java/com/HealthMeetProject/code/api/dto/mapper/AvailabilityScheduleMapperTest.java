package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class AvailabilityScheduleMapperTest {
    private AvailabilityScheduleMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new AvailabilityScheduleMapperImpl();
    }

    @Test
    public void shouldMapFromEntity() {
        //given
        AvailabilitySchedule availabilitySchedule = new AvailabilitySchedule();
        availabilitySchedule.setAvailability_schedule_id(1);
        availabilitySchedule.setAvailableTerm(true);
        availabilitySchedule.setAvailableDay(true);
        availabilitySchedule.setDoctor(DoctorExampleFixtures.doctorExample3());
        availabilitySchedule.setSince(OffsetDateTime.of(LocalDateTime.of(2025, 2, 12, 14, 20), ZoneOffset.UTC));
        availabilitySchedule.setToWhen(OffsetDateTime.of(LocalDateTime.of(2025, 2, 12, 20, 20), ZoneOffset.UTC));
        //when
        AvailabilityScheduleDTO availabilityScheduleDTO = mapper.mapToDTO(availabilitySchedule);
        //then
        assertEquals(availabilitySchedule, availabilityScheduleDTO);
    }

    @Test
    public void shouldMapToEntity() {
        //given
        AvailabilityScheduleDTO availabilityScheduleDTO = new AvailabilityScheduleDTO();
        availabilityScheduleDTO.setAvailability_schedule_id(5);
        availabilityScheduleDTO.setAvailableTerm(false);
        availabilityScheduleDTO.setAvailableDay(false);
        availabilityScheduleDTO.setDoctor(DoctorExampleFixtures.doctorDTOExample3());
        availabilityScheduleDTO.setSince(OffsetDateTime.of(LocalDateTime.of(2025, 3, 12, 14, 20), ZoneOffset.UTC));
        availabilityScheduleDTO.setToWhen(OffsetDateTime.of(LocalDateTime.of(2025, 3, 12, 20, 20), ZoneOffset.UTC));
        //when
        AvailabilitySchedule availabilitySchedule = mapper.mapFromDTO(availabilityScheduleDTO);
        //then
        assertEquals(availabilitySchedule, availabilityScheduleDTO);
    }

    private static void assertEquals(AvailabilitySchedule availabilitySchedule, AvailabilityScheduleDTO availabilityScheduleDTO) {
        Assertions.assertEquals(availabilitySchedule.getAvailability_schedule_id(), availabilityScheduleDTO.getAvailability_schedule_id());
        Assertions.assertEquals(availabilitySchedule.isAvailableTerm(), availabilityScheduleDTO.isAvailableTerm());
        Assertions.assertEquals(availabilitySchedule.isAvailableDay(), availabilityScheduleDTO.isAvailableDay());
        Assertions.assertEquals(availabilitySchedule.getDoctor().getName(), availabilityScheduleDTO.getDoctor().getName());
        Assertions.assertEquals(availabilitySchedule.getDoctor().getSurname(), availabilityScheduleDTO.getDoctor().getSurname());
        Assertions.assertEquals(availabilitySchedule.getDoctor().getSpecialization(), availabilityScheduleDTO.getDoctor().getSpecialization());
        Assertions.assertEquals(availabilitySchedule.getDoctor().getEmail(), availabilityScheduleDTO.getDoctor().getEmail());
        Assertions.assertEquals(availabilitySchedule.getDoctor().getPhone(), availabilityScheduleDTO.getDoctor().getPhone());
        Assertions.assertEquals(availabilitySchedule.getDoctor().getEarningsPerVisit(), availabilityScheduleDTO.getDoctor().getEarningsPerVisit());
        Assertions.assertNull(availabilitySchedule.getDoctor().getUser());
    }
}