package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Address;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientDAO patientDAO;

    @Mock
    private MeetingRequestService meetingRequestService;

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;


    @Test
    void testRegister() throws UserAlreadyExistsException {
        // given
        PatientDTO patientDTO = new PatientDTO();
        //when
        Mockito.doNothing().when(patientDAO).register(patientDTO);

        patientService.register(patientDTO);
        //them
        verify(patientDAO, Mockito.times(1)).register(patientDTO);
    }

    @Test
    void testAuthenticate() {
        //given
        String username = "testuser";
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("PATIENT")));

        //when
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.setContext(securityContext);
        String authenticatedEmail = patientService.authenticate();

        //then
        assertEquals(username, authenticatedEmail);
    }

    @Test
    void testConditionsToUpdate() {
        PatientDTO updatedPatientDTO = new PatientDTO();
        updatedPatientDTO.setName("John");
        updatedPatientDTO.setSurname("Doe");
        updatedPatientDTO.setEmail("john.doe@example.com");
        updatedPatientDTO.setPhone("123-456-789");
        updatedPatientDTO.setAddress(new Address(3L, "Polska", "Warsaw", "96-323", "ul. szkolna 3"));

        Patient existingPatient = PatientExampleFixtures.patientExample1();
        existingPatient.setUser(new UserData(
                3, "testsurname", "password", "test@email.com", true, Set.of(new RoleEntity(2, "PATIENT"))));
        patientService.conditionsToUpdate(updatedPatientDTO, existingPatient);

        assertEquals("John", existingPatient.getName());
        assertEquals("Doe", existingPatient.getSurname());
        assertEquals("john.doe@example.com", existingPatient.getEmail());
        assertEquals("123-456-789", existingPatient.getPhone());
        assertEquals("Polska", existingPatient.getAddress().getCountry());
        assertEquals("Warsaw", existingPatient.getAddress().getCity());
        assertEquals("96-323", existingPatient.getAddress().getPostalCode());
        assertEquals("ul. szkolna 3", existingPatient.getAddress().getAddress());

    }
}
