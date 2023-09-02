package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
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
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

//    @Test
//    public void testRegisterNewDoctor() {
//        // Mockowanie danych testowych
//        DoctorDTO doctorDTO = createValidDoctorDTO();
//        when(doctorJpaRepository.findByEmail(doctorDTO.getEmail())).thenReturn(Optional.empty());
//        when(doctorEntityMapper.mapToEntity(any())).thenReturn(createDoctorEntity(doctorDTO));
//        when(roleRepository.findByRole("DOCTOR")).thenReturn(createRoleEntity());
//
//        // Wywołanie metody do przetestowania
//        doctorRepository.register(doctorDTO);
//
//        // Sprawdzenie czy metody były wywołane odpowiednią ilość razy
//        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
//        verify(doctorJpaRepository, times(1)).saveAndFlush(any(DoctorEntity.class));
//    }

    //    @Test
//    public void testRegisterDoctorWithDuplicateEmail() {
//        DoctorDTO doctorDTO = DoctorDTOFixtures.getDoctorDTOToRegister();
//        when(doctorJpaRepository.findByEmail(doctorDTO.getEmail())).thenReturn(Optional.of(createDoctorEntity(doctorDTO)));
//
//        assertThrows(UserAlreadyExistsException.class, () -> doctorRepository.register(doctorDTO));
//
//        verify(userRepository, never()).saveAndFlush(any(UserEntity.class));
//        verify(doctorJpaRepository, never()).saveAndFlush(any(DoctorEntity.class));
//    }
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testRegisterDoctorWithDuplicatePhone() {
        //given
        DoctorDTO doctorDTO = DoctorDTOFixtures.getDoctorDTOToRegister();
        //when
        Mockito.when(doctorJpaRepository.findByEmail(doctorDTO.getEmail())).thenReturn(Optional.of(DoctorDTOFixtures.getDoctorEntityToRegister()));
        //then
        assertThrows(UserAlreadyExistsException.class, () -> doctorRepository.register(doctorDTO));

        verify(userRepository, never()).saveAndFlush(any(UserEntity.class));
        verify(doctorJpaRepository, never()).saveAndFlush(any(DoctorEntity.class));
    }

}
