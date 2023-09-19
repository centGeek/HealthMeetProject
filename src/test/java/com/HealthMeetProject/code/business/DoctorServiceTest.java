package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.repository.NoteRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorDAO doctorDAO;
    @Mock
    private DoctorEntityMapper doctorEntityMapper;
    @Mock
    private DoctorMapper doctorMapper;
    @Mock
    private PatientEntityMapper patientEntityMapper;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @InjectMocks
    private DoctorService doctorService;

    @Test
    void testUserHasPatientPermission() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("PATIENT")));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        assertTrue(doctorService.userHasPatientPermission());
    }

    @Test
    void testUserDoesNotHavePatientPermission() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("OTHER_ROLE")));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        assertFalse(doctorService.userHasPatientPermission());
    }
    @Test
    void findAllAvailableDoctorsTest() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("PATIENT")));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        List<Doctor> test = List.of(DoctorExampleFixtures.doctorExample1(),
                DoctorExampleFixtures.doctorExample3());
        when(doctorDAO.findAllAvailableDoctors()).thenReturn(test);

        List<Doctor> result = doctorService.findAllAvailableDoctors();
        Assertions.assertEquals(result, test);
    }
    @Test
    void testFindByEmailDoctorFound() {
        Doctor sampleDoctor = DoctorExampleFixtures.doctorExample1();
        when(doctorDAO.findByEmail("existent@example.com")).thenReturn(Optional.of(sampleDoctor));

        Doctor foundDoctor = doctorService.findByEmail("existent@example.com");
        assertNotNull(foundDoctor);
    }
    @Test
    void testFindByEmailDoctorNotFound() {
        when(doctorDAO.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                doctorService.findByEmail("nonexistent@example.com"));

        String expectedMessage = "Can not find doctor by email: [nonexistent@example.com]";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
    @Test
    void testWriteNoteSuccessful() {
        when(noteRepository.isThereNoteWithTheSameTimeVisitAndDoctor(any(), any(), any())).thenReturn(false);

        assertDoesNotThrow(() -> doctorService.writeNote(DoctorExampleFixtures.doctorExample1(), "illness", "description",
                PatientExampleFixtures.patientExample1(), LocalDateTime.now(), LocalDateTime.now()));

    }

    @Test
    void testWriteNoteNoteAlreadyExists() {
        when(noteRepository.isThereNoteWithTheSameTimeVisitAndDoctor(any(), any(), any())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> doctorService.writeNote(DoctorExampleFixtures.doctorExample1(), "illness", "description",
                PatientExampleFixtures.patientExample1(), LocalDateTime.now(), LocalDateTime.now()));

        String expectedMessage = "Note with following visit already exist";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void testConditionsToUpdate() {
        DoctorDTO updatedDoctor = new DoctorDTO();
        updatedDoctor.setName("NewName");
        updatedDoctor.setSurname("NewSurname");
        updatedDoctor.setSpecialization(Specialization.PULMONOLOGIST);
        updatedDoctor.setEmail("new.email@example.com");
        updatedDoctor.setPhone("123456789");
        updatedDoctor.setEarningsPerVisit(BigDecimal.valueOf(100.0));


        UserData user = new UserData();
        user.setUserName("username");
        user.setEmail("surname@name.com");
        user.setPassword("test");
        user.setActive(true);
        user.setRoles(Set.of(new RoleEntity(1, "DOCTOR")));


        Doctor existingDoctor = new Doctor();
        existingDoctor.setName("OldName");
        existingDoctor.setSurname("OldSurname");
        existingDoctor.setSpecialization(Specialization.PEDIATRICIAN);
        existingDoctor.setEmail("old.email@example.com");
        existingDoctor.setPhone("987654321");
        existingDoctor.setEarningsPerVisit(BigDecimal.valueOf(50.0));
        existingDoctor.setUser(user);

        doctorService.conditionsToUpdate(updatedDoctor, existingDoctor);

        assertEquals("NewName", existingDoctor.getName());
        assertEquals("NewSurname", existingDoctor.getSurname());
        assertEquals(Specialization.PULMONOLOGIST, existingDoctor.getSpecialization());
        assertEquals("new.email@example.com", existingDoctor.getEmail());
        assertEquals("123456789", existingDoctor.getPhone());
        assertEquals(BigDecimal.valueOf(100.0), existingDoctor.getEarningsPerVisit());
    }
}