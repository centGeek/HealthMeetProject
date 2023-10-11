package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import com.HealthMeetProject.code.util.ReceiptExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptEntityMapperTest {
    private ReceiptEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(ReceiptEntityMapper.class);
    }

    @Test
    public void shouldMapReceiptEntityToPatient() {
        // given
        ReceiptEntity receiptEntity = ReceiptExampleFixtures.receiptEntityExampleData1();
        receiptEntity.setDoctor(DoctorExampleFixtures.doctorEntityExample1());
        receiptEntity.setMedicine(Set.of(MedicineExampleFixtures.medicineEntityExampleData1()));
        //when
        Receipt receipt = mapper.mapFromEntity(receiptEntity);

        // then
        assertions(receipt, receiptEntity);
        Assertions.assertNull(receipt.getMedicine());
        Assertions.assertNotNull(receiptEntity.getMedicine());
    }

    @Test
    public void shouldMapPatientToReceiptEntity() {
        // given
        Receipt receipt = ReceiptExampleFixtures.receiptExampleData1();
        receipt.setDoctor(DoctorExampleFixtures.doctorExample1());
        receipt.setMedicine(Set.of(MedicineExampleFixtures.medicineExampleData1()));

        //when
        ReceiptEntity receiptEntity = mapper.mapToEntity(receipt);
        //then
        assertions(receipt, receiptEntity);
        Assertions.assertNotNull(receipt.getMedicine());
        Assertions.assertNull(receiptEntity.getMedicine());
    }

    private static void assertions(Receipt receipt, ReceiptEntity receiptEntity) {
        assertEquals(receipt.getReceiptId(), receiptEntity.getReceiptId());
        assertEquals(receipt.getPatient().getPhone(), receiptEntity.getPatient().getPhone());
        assertEquals(receipt.getDateTime(), receiptEntity.getDateTime());
        assertEquals(receipt.getReceiptNumber(), receiptEntity.getReceiptNumber());
        assertEquals(receipt.getDoctor().getPhone(), receiptEntity.getDoctor().getPhone());
    }

    @Test
    public void ReceiptEntityTest() {
        ReceiptEntity receiptEntity = new ReceiptEntity();
        ReceiptEntity receiptEntity1 = receiptEntity
                .withDateTime(LocalDateTime.MAX)
                .withReceiptId(3)
                .withReceiptNumber("323123124")
                .withMedicine(Set.of())
                .withPatient(PatientExampleFixtures.patientEntityExample1());

        receiptEntity.setDateTime(LocalDateTime.MAX);
        receiptEntity.setReceiptId(3);
        receiptEntity.setReceiptNumber("323123124");
        receiptEntity.setMedicine(Set.of());
        receiptEntity.setPatient(PatientExampleFixtures.patientEntityExample1());
        Assertions.assertEquals(receiptEntity1, receiptEntity);
        Assertions.assertEquals(receiptEntity1.hashCode(), receiptEntity.hashCode());
    }

}