package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.NoteEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.shaded.org.bouncycastle.cert.dane.DANEEntryFactory;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DoctorRepositoryTest {

    @Mock
    private DoctorJpaRepository doctorJpaRepository;

    @Mock
    private DoctorEntityMapper doctorEntityMapper;

    @Mock
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;

    @Mock
    private NoteJpaRepository noteJpaRepository;

    @Mock
    private NoteEntityMapper noteEntityMapper;

    @Mock
    private ReceiptJpaRepository receiptJpaRepository;

    @Mock
    private ReceiptEntityMapper receiptEntityMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorRepository doctorRepository;

    @Test
    public void testFindByEmail() {
        //given
        String email = "doctor@example.com";
        Doctor expectedDoctor = new Doctor();
        //when
        when(doctorJpaRepository.findByEmail(email)).thenReturn(Optional.of(new DoctorEntity()));
        when(doctorEntityMapper.mapFromEntity(any())).thenReturn(expectedDoctor);

        Optional<Doctor> result = doctorRepository.findByEmail(email);
        //then
        assertTrue(result.isPresent());
        assertEquals(expectedDoctor, result.get());
    }

    @Test
    public void testFindByEmail_NotFound() {
        //given
        String email = "nonexistent@example.com";
        //when
        when(doctorJpaRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Doctor> result = doctorRepository.findByEmail(email);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void testRegister() {
        //given
        DoctorDTO doctorDTO = DoctorExampleFixtures.doctorDTOExample3();
        RoleEntity role = RoleEntity.builder().id(1).role("DOCTOR").build();
        when(roleRepository.findByRole(role.getRole())).thenReturn(role);
        doctorDTO.setUser(new UserData(1, "test", "test"
                , "test@email.com", true, Set.of(role)));
        //when
        doctorRepository.register(doctorDTO);
        //then
        verify(userRepository, times(1)).saveAndFlush(any());
        verify(doctorJpaRepository, times(1)).saveAndFlush(any());
    }

}
