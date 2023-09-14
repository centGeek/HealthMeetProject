package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvailabilityScheduleServiceTest {

    @Mock
    private AvailabilityScheduleDAO availabilityScheduleDAO;
    @Mock
    private AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    @Mock
    private AvailabilityScheduleMapper availabilityScheduleMapper;
    @Mock
    private  DoctorService doctorService;
    @InjectMocks
    private AvailabilityScheduleService availabilityScheduleService;


    @Test
    public void addingTooShortTerm() {
        //given,when
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        availabilitySchedule1.setSince(OffsetDateTime.now());
        DoctorExampleFixtures.availabilitySchedule1().setToWhen(OffsetDateTime.now().plusMinutes(14).plusSeconds(59));


        OffsetDateTime since = availabilitySchedule1.getSince();
        OffsetDateTime toWhen = availabilitySchedule1.getToWhen();

        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();

        //then
        assertThrows(ProcessingException.class, () -> availabilityScheduleService.addTerm(since, toWhen, doctorEntity));
    }

    @Test
    public void addingTooLongTerm() {
        //given,when
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        availabilitySchedule1.setSince(OffsetDateTime.now());
        DoctorExampleFixtures.availabilitySchedule1().setToWhen(OffsetDateTime.now().plusMinutes(10).plusSeconds(1));


        OffsetDateTime since = availabilitySchedule1.getSince();
        OffsetDateTime toWhen = availabilitySchedule1.getToWhen();

        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();

        //then
        assertThrows(ProcessingException.class, () -> availabilityScheduleService.addTerm(since, toWhen, doctorEntity));
    }
    @Test
    public void addingTermProperly() {
        //given
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        AvailabilityScheduleDTO availabilityScheduleDTO1 = DoctorExampleFixtures.availabilityScheduleDTO1();
        OffsetDateTime since = availabilitySchedule1.getSince();
        OffsetDateTime toWhen = availabilitySchedule1.getToWhen();
        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();
        //when
        when(availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity)).thenReturn(DoctorExampleFixtures.availabilitySchedule1());
        when(availabilityScheduleMapper.map(availabilitySchedule1)).thenReturn(availabilityScheduleDTO1);
        when(doctorService.findAnyTermInGivenRangeInGivenDay(any(), any(), any())).thenReturn(true);
        AvailabilityScheduleDTO availabilityScheduleDTO = availabilityScheduleService.addTerm(since, toWhen, doctorEntity);
        //then
        assertEquals(availabilityScheduleDTO.getSince(), since);
        assertEquals(availabilityScheduleDTO.getToWhen(), toWhen);
        assertEquals(availabilityScheduleDTO.getDoctor(), DoctorExampleFixtures.doctorDTOExample3());
        assertTrue(availabilityScheduleDTO.isAvailableTerm());
        assertTrue(availabilityScheduleDTO.isAvailableDay());
    }

    @Test
    void testFindAllAvailableTermsByGivenDoctor() {
        String doctorEmail = "test@example.com";
        List<AvailabilityScheduleDTO> availabilityScheduleDTOList = new ArrayList<>();
        when(availabilityScheduleDAO.findAllAvailableTermsByGivenDoctor(doctorEmail).stream().map(availabilityScheduleMapper::map).toList())
                .thenReturn(availabilityScheduleDTOList);

        List<AvailabilityScheduleDTO> result = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctorEmail);
        assertEquals(availabilityScheduleDTOList, result);
    }
    @Test
    void testFindAllTermsByGivenDoctor() {
        String doctorEmail = "test@example.com";
        List<AvailabilityScheduleDTO> availabilityScheduleDTOList = new ArrayList<>();
        when(availabilityScheduleDAO.findAllAvailableTermsByGivenDoctor(doctorEmail).stream().map(availabilityScheduleMapper::map).toList())
                .thenReturn(availabilityScheduleDTOList);

        List<AvailabilityScheduleDTO> result = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctorEmail);
        assertEquals(availabilityScheduleDTOList, result);
    }


}