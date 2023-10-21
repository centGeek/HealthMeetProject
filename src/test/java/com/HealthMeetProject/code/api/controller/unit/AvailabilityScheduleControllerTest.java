package com.HealthMeetProject.code.api.controller.unit;

import com.HealthMeetProject.code.api.controller.AvailabilityScheduleController;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvailabilityScheduleControllerTest {
    @InjectMocks
    private AvailabilityScheduleController controller;

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorEntityMapper doctorEntityMapper;

    @Mock
    private DoctorMapper doctorMapper;

    @Mock
    private AvailabilityScheduleService availabilityScheduleService;

    @Mock
    private AvailabilityScheduleDAO availabilityScheduleDAO;


    @Test
    void addTerms() {
        //given
        String since = "2023-09-30 10:30 AM";
        String toWhen = "2023-09-30 10:45 AM";
        String email = "doctor@example.com";
        //when
        when(availabilityScheduleService.parseToLocalDateTime(since)).thenReturn(LocalDateTime.of(2023, 9, 23, 10, 45));
        when(availabilityScheduleService.parseToLocalDateTime(toWhen)).thenReturn(LocalDateTime.of(2023, 9, 23, 11, 0));

        Doctor byEmail = DoctorExampleFixtures.doctorExample1();
        byEmail.setEmail(email);
        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample1();
        doctorEntity.setEmail(email);

        when(doctorService.authenticateDoctor()).thenReturn(email);
        when(doctorService.findByEmail(email)).thenReturn(byEmail);
        when(doctorEntityMapper.mapToEntity(byEmail)).thenReturn(doctorEntity);

        String redirectPath = controller.addTerms(since, toWhen);

        //then
        Assertions.assertEquals(redirectPath, "redirect:/doctor");
        verify(doctorService).authenticateDoctor();
        verify(doctorService).findByEmail(email);
        verify(doctorEntityMapper).mapToEntity(byEmail);
        verify(availabilityScheduleService).addTerm(availabilityScheduleService.parseToLocalDateTime(since),
                availabilityScheduleService.parseToLocalDateTime(toWhen), doctorEntity);
    }

    @Test
    void deleteTerm_ShouldRedirectToDoctorView() {
        //given
        Integer availabilityScheduleId = 1;

        //when
        controller.deleteTerm(availabilityScheduleId);

        //then
        verify(availabilityScheduleDAO).deleteById(availabilityScheduleId);
    }
}
