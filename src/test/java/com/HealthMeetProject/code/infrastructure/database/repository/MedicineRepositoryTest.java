package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MedicineJpaRepository;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicineRepositoryTest {

    @Mock
    private MedicineJpaRepository medicineJpaRepository;


    @Test
    public void testFindByReceipt() {
        Integer receiptId = 1;
        MedicineEntity medicineEntity1 = new MedicineEntity();
        MedicineEntity medicineEntity2 = new MedicineEntity();
        medicineEntity1.setReceipt(ReceiptEntity.builder().receiptId(receiptId).build());
        medicineEntity2.setReceipt(ReceiptEntity.builder().receiptId(receiptId).build());
        List<MedicineEntity> expectedMedicines = Arrays.asList(medicineEntity1, medicineEntity2);

        when(medicineJpaRepository.findByReceipt(receiptId)).thenReturn(expectedMedicines);

        List<MedicineEntity> result = medicineJpaRepository.findByReceipt(receiptId);

        verify(medicineJpaRepository, times(1)).findByReceipt(receiptId);

        assertEquals(expectedMedicines, result);

        for (MedicineEntity medicineEntity : result) {
            assertEquals(receiptId, medicineEntity.getReceipt().getReceiptId());
        }
    }

    @Test
    public void testFindByPatientEmail() {
        //given
        String patientEmail = "patient@example.com";
        List<MedicineEntity> medicineEntities = List.of(MedicineExampleFixtures.medicineEntityExampleData1(),
                MedicineExampleFixtures.medicineEntityExampleData2());
        //when
        when(medicineJpaRepository.findByPatientEmail(patientEmail)).thenReturn(medicineEntities);

        List<MedicineEntity> result = medicineJpaRepository.findByPatientEmail(patientEmail);
        //then
        assertEquals(medicineEntities.size(), result.size());
        assertEquals(medicineEntities.get(0), result.get(0));
    }

    @Test
    public void testFindByReceipt_EmptyList() {
        //given
        Integer receiptId = 2;
        //when


        List<MedicineEntity> result = medicineJpaRepository.findByReceipt(receiptId);
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByPatientEmail_EmptyList() {
        //given
        String patientEmail = "anotherpatient@example.com";
        //when
        when(medicineJpaRepository.findByPatientEmail(patientEmail)).thenReturn(List.of());

        List<MedicineEntity> result = medicineJpaRepository.findByPatientEmail(patientEmail);
        //then
        assertEquals(0, result.size());
    }

}