package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MedicineJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicineRepositoryTest {

    @Mock
    private MedicineJpaRepository medicineJpaRepository;
    @Mock
    private MedicineEntityMapper medicineEntityMapper;
    @InjectMocks
    private MedicineRepository medicineRepository;


    @Test
    public void testFindByReceipt() {
        //given
        Integer receiptId = 1;
        List<Medicine> medicineEntities = List.of(MedicineExampleFixtures.medicineExampleData1(),MedicineExampleFixtures.medicineExampleData2());
        //when
        when(medicineJpaRepository.findByReceipt(receiptId).stream().map(medicineEntityMapper::mapFromEntity).toList()).thenReturn(medicineEntities);

        List<MedicineEntity> result = medicineRepository.findByReceipt(receiptId);
        //then
        assertEquals(medicineEntities.size(), result.size());
    }

    @Test
    public void testFindByPatientEmail() {
        //given
        String patientEmail = "patient@example.com";
        List<MedicineEntity> medicineEntities = List.of(new MedicineEntity(), new MedicineEntity());
        //when
        when(medicineJpaRepository.findByPatientEmail(patientEmail)).thenReturn(medicineEntities);

        List<MedicineEntity> result = medicineRepository.findByPatientEmail(patientEmail);
        //then
        assertEquals(medicineEntities.size(), result.size());
    }

    @Test
    public void testFindByReceipt_EmptyList() {
        //given
        Integer receiptId = 2;
        //when

        when(medicineJpaRepository.findByReceipt(receiptId).stream().map(medicineEntityMapper::mapFromEntity).toList()).thenReturn(List.of());

        List<MedicineEntity> result = medicineRepository.findByReceipt(receiptId);
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByPatientEmail_EmptyList() {
        //given
        String patientEmail = "anotherpatient@example.com";
        //when
        when(medicineJpaRepository.findByPatientEmail(patientEmail)).thenReturn(List.of());

        List<MedicineEntity> result = medicineRepository.findByPatientEmail(patientEmail);
        //then
        assertEquals(0, result.size());
    }

}