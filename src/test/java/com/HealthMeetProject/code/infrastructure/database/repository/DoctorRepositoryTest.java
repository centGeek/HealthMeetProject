package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.*;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.ReceiptExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
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
    private ReceiptJpaRepository receiptJpaRepository;
    @Mock
    private ReceiptEntityMapper receiptEntityMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MedicineJpaRepository medicineJpaRepository;
    @Mock
    private DoctorMapper doctorMapper;
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
    @Test
    public void testWriteNote() {
        //given
        NoteEntity noteEntity = new NoteEntity();

        //when
        doctorRepository.writeNote(noteEntity);

        //then
        verify(noteJpaRepository, times(1)).saveAndFlush(noteEntity);
    }

    @Test
    public void testIssueReceipt() {
        //given
        Receipt receipt = ReceiptExampleFixtures.receiptExampleData1();
        MedicineEntity medicineEntity = MedicineExampleFixtures.medicineEntityExampleData3();
        Set<MedicineEntity> medicineSet = Collections.singleton(medicineEntity);
        ReceiptEntity receiptEntity = ReceiptExampleFixtures.receiptEntityExampleData1();

        when(receiptEntityMapper.mapToEntity(receipt)).thenReturn(receiptEntity);
        when(receiptJpaRepository.saveAndFlush(receiptEntity)).thenReturn(receiptEntity);
        when(receiptJpaRepository.findByReceiptNumber(any())).thenReturn(receiptEntity);

        //when
        doctorRepository.issueReceipt(receipt, medicineSet);

        //then
        verify(receiptEntityMapper, times(1)).mapToEntity(receipt);
        verify(receiptJpaRepository, times(1)).saveAndFlush(receiptEntity);
        verify(medicineJpaRepository, times(1)).saveAllAndFlush(medicineSet);
    }

    @Test
    public void testFindAllAvailableDoctors() {
        //given
        DoctorEntity doctorEntity1 = DoctorExampleFixtures.doctorEntityExample1();
        DoctorEntity doctorEntity2 = DoctorExampleFixtures.doctorEntityExample2();
        Doctor doctor1 = DoctorExampleFixtures.doctorExample1();
        Doctor doctor2 = DoctorExampleFixtures.doctorExample3();
        List<DoctorEntity> doctorsEntity = List.of(doctorEntity1, doctorEntity2);
        doctor2.setSpecialization(Specialization.PSYCHIATRIST);
        when(doctorJpaRepository.findAll()).thenReturn(doctorsEntity);
        when(doctorEntityMapper.mapFromEntity(doctorEntity1)).thenReturn(doctor1);
        when(doctorEntityMapper.mapFromEntity(doctorEntity2)).thenReturn(doctor2);

        //when
        List<Doctor> result = doctorRepository.findAllAvailableDoctors();

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(doctorEntity1.getEmail(), result.get(0).getEmail());
    }

}
