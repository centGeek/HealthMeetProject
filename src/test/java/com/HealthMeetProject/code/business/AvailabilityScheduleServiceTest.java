package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
        availabilitySchedule1.setSince(LocalDateTime.now());
        DoctorExampleFixtures.availabilitySchedule1().setToWhen(LocalDateTime.now().plusMinutes(14).plusSeconds(59));


        LocalDateTime since = availabilitySchedule1.getSince();
        LocalDateTime toWhen = availabilitySchedule1.getToWhen();

        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();

        //then
        assertThrows(ProcessingException.class, () -> availabilityScheduleService.addTerm(since, toWhen, doctorEntity));
    }

    @Test
    public void addingTooLongTerm() {
        //given,when
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        availabilitySchedule1.setSince(LocalDateTime.now());
        DoctorExampleFixtures.availabilitySchedule1().setToWhen(LocalDateTime.now().plusHours(10).plusSeconds(1));


        LocalDateTime since = availabilitySchedule1.getSince();
        LocalDateTime toWhen = availabilitySchedule1.getToWhen();

        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();

        //then
        assertThrows(ProcessingException.class, () -> availabilityScheduleService.addTerm(since, toWhen, doctorEntity));
    }
    @Test
    public void addingImpossibleTerm() {
        //given,when
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        availabilitySchedule1.setSince(LocalDateTime.now());
        DoctorExampleFixtures.availabilitySchedule1().setToWhen(LocalDateTime.now().minusMinutes(10).plusSeconds(1));


        LocalDateTime since = availabilitySchedule1.getSince();
        LocalDateTime toWhen = availabilitySchedule1.getToWhen();

        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();

        //then
        assertThrows(ProcessingException.class, () -> availabilityScheduleService.addTerm(since, toWhen, doctorEntity));
    }
    @Test
    public void thatIsNotAMinimumPlannedVisit() {
        //given,when
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        availabilitySchedule1.setSince(LocalDateTime.now());
        DoctorExampleFixtures.availabilitySchedule1().setToWhen(LocalDateTime.now().plusMinutes(10).plusSeconds(1));


        LocalDateTime since = availabilitySchedule1.getSince();
        LocalDateTime toWhen = availabilitySchedule1.getToWhen();

        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();

        //then
        assertThrows(ProcessingException.class, () -> availabilityScheduleService.addTerm(since, toWhen, doctorEntity));
    }
    @Test
    public void addingTermProperly() {
        //given
        AvailabilitySchedule availabilitySchedule1 = DoctorExampleFixtures.availabilitySchedule1();
        AvailabilityScheduleDTO availabilityScheduleDTO1 = DoctorExampleFixtures.availabilityScheduleDTO1();
        LocalDateTime since = availabilitySchedule1.getSince();
        LocalDateTime toWhen = availabilitySchedule1.getToWhen();
        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample3();
        //when
        when(availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity)).thenReturn(DoctorExampleFixtures.availabilitySchedule1());
        when(availabilityScheduleMapper.mapToDTO(availabilitySchedule1)).thenReturn(availabilityScheduleDTO1);
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
    public void testFindAllTermsByGivenDoctor() {
        //given
        String doctorEmail = "doctor@example.com";
        AvailabilityScheduleDTO expectedDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
        AvailabilitySchedule expected = DoctorExampleFixtures.availabilitySchedule1();
        when(availabilityScheduleDAO.findAllTermsByGivenDoctor(doctorEmail)).thenReturn(List.of(expected));
        when(availabilityScheduleMapper.mapToDTO(any())).thenReturn(expectedDTO);

        //when
        List<AvailabilityScheduleDTO> result = availabilityScheduleService.findAllTermsByGivenDoctor(doctorEmail);

        //then
        assertEquals(1, result.size());
        assertSame(expectedDTO, result.get(0));
    }

    @Test
    public void testParseToLocalDateTime() {
        //given
        String dateTimeString = "2023-10-06 10:30 AM";
        LocalDateTime expectedDateTime = LocalDateTime.of(2023, 10, 6, 10, 30);

        //when
        LocalDateTime result = availabilityScheduleService.parseToLocalDateTime(dateTimeString);

        //then
        assertEquals(expectedDateTime, result);
    }

    @Test
    public void testFindAllAvailableTermsByGivenDoctor() {
        //given
        String doctorEmail = "doctor@example.com";
        AvailabilityScheduleDTO expectedDTO = DoctorExampleFixtures.availabilityScheduleDTO1();
        AvailabilitySchedule expected = DoctorExampleFixtures.availabilitySchedule1();

        when(availabilityScheduleDAO.findAllAvailableTermsByGivenDoctor(doctorEmail)).thenReturn(List.of(expected));
        when(availabilityScheduleMapper.mapToDTO(any())).thenReturn(expectedDTO);

        //when
        List<AvailabilityScheduleDTO> result = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctorEmail);

        //then
        assertEquals(1, result.size());
        assertSame(expectedDTO, result.get(0));
    }

    @Test
    public void testSave() {
        //given
        AvailabilitySchedule availabilitySchedule = new AvailabilitySchedule();
        AvailabilityScheduleEntity availabilityScheduleEntity = DoctorExampleFixtures.availabilityScheduleEntity1();

        when(availabilityScheduleEntityMapper.mapToEntity(availabilitySchedule)).thenReturn(availabilityScheduleEntity);

        //when
        availabilityScheduleService.save(availabilitySchedule);

        //then
        Mockito.verify(availabilityScheduleDAO, Mockito.times(1)).save(availabilityScheduleEntity);
    }
}