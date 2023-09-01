package com.HealthMeetProject.code.infrastructure.database.repository;

import static org.mockito.Mockito.*;

import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.NoteEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorRepository = new DoctorRepository(
                doctorJpaRepository,
                doctorEntityMapper,
                availabilityScheduleJpaRepository,
                noteJpaRepository,
                noteEntityMapper,
                receiptJpaRepository,
                receiptEntityMapper,
                roleRepository,
                userRepository,
                passwordEncoder
        );
    }

    @Test
    public void thatDoctorIsRegisteredProperly(){

    }

}
