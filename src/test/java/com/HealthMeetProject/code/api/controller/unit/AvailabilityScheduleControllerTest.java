package com.HealthMeetProject.code.api.controller.unit;

import com.HealthMeetProject.code.api.controller.AvailabilityScheduleController;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

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
    void showYourAvailableTermsTest() {
        // Arrange
        Model model = new RedirectAttributesModelMap();
        String email = "doctor@example.com";
        DoctorDTO doctorDTO = DoctorDTOFixtures.getDoctorDTO1();
        doctorDTO.setEmail(email);

        List<AvailabilityScheduleDTO> availabilitySchedules = new ArrayList<>();
        when(doctorService.authenticateDoctor()).thenReturn(email);
        when(doctorService.findByEmail(email)).thenReturn(DoctorExampleFixtures.doctorExample1());
        when(doctorMapper.mapToDTO(any(Doctor.class))).thenReturn(doctorDTO);
        when(availabilityScheduleService.findAllTermsByGivenDoctor(email)).thenReturn(availabilitySchedules);

        // Act
        String viewName = controller.showYourAvailableTerms(model);

        // Assert
        verify(doctorService).authenticateDoctor();
        verify(doctorService).findByEmail(email);
        verify(doctorMapper).mapToDTO(any(Doctor.class));
        verify(availabilityScheduleService).findAllTermsByGivenDoctor(email);
    }

    @Test
    void addTerms() {
        // Arrange
        String since = "2023-09-30 10:30 AM";
        String toWhen = "2023-09-30 10:45 AM";
        String email = "doctor@example.com";
        when(availabilityScheduleService.parseToLocalDateTime(since)).thenReturn(LocalDateTime.of(2023, 9, 23, 10, 45));
        when(availabilityScheduleService.parseToLocalDateTime(toWhen)).thenReturn(LocalDateTime.of(2023, 9, 23, 11, 0));

        Doctor byEmail = DoctorExampleFixtures.doctorExample1();
        byEmail.setEmail(email);
        DoctorEntity doctorEntity = DoctorExampleFixtures.doctorEntityExample1();
        doctorEntity.setEmail(email);

        when(doctorService.authenticateDoctor()).thenReturn(email);
        when(doctorService.findByEmail(email)).thenReturn(byEmail);
        when(doctorEntityMapper.mapToEntity(byEmail)).thenReturn(doctorEntity);

        // Act
        String redirectPath = controller.addTerms(since, toWhen);

        // Assert
        Assertions.assertEquals(redirectPath, "redirect:/doctor");
        verify(doctorService).authenticateDoctor();
        verify(doctorService).findByEmail(email);
        verify(doctorEntityMapper).mapToEntity(byEmail);
        verify(availabilityScheduleService).addTerm(availabilityScheduleService.parseToLocalDateTime(since),
                availabilityScheduleService.parseToLocalDateTime(toWhen), doctorEntity);
    }

    @Test
    void deleteTerm_ShouldRedirectToDoctorView() {
        // Arrange
        Integer availabilityScheduleId = 1;

        // Act
        controller.deleteTerm(availabilityScheduleId);

        // Assert
        verify(availabilityScheduleDAO).deleteById(availabilityScheduleId);
    }
}
