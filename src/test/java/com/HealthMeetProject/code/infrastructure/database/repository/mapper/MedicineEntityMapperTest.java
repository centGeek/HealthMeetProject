package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.util.ReceiptExampleFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MedicineEntityMapperTest {

    private MedicineEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(MedicineEntityMapper.class);
    }

    @Test
    public void shouldMapMedicineToMedicineEntity() {
        // given
        Medicine medicine = new Medicine();
        medicine.setMedicineId(1);
        medicine.setName("Ibuprofen");
        medicine.setReceipt(ReceiptExampleFixtures.receiptExampleData1());
        medicine.setApproxPrice(BigDecimal.TEN);
        medicine.setQuantity(3);
        //when
        MedicineEntity medicineEntity = mapper.mapToEntity(medicine);

        // then
        assertions(medicineEntity,medicine);
        assertNotEquals(medicineEntity.hashCode(),medicine.hashCode());
    }

    @Test
    public void shouldMapMedicineEntityToMedicine() {
        // given
        MedicineEntity medicineEntity = new MedicineEntity();
        medicineEntity.setMedicineId(1);
        medicineEntity.setName("Ibuprofen");
        medicineEntity.setReceipt(ReceiptExampleFixtures.receiptEntityExampleData1());
        medicineEntity.setApproxPrice(BigDecimal.TEN);
        medicineEntity.setQuantity(3);
        //when
        Medicine medicine = mapper.mapFromEntity(medicineEntity);
        //then
        Assertions.assertThat(medicineEntity.getMedicineId()).isEqualTo(medicine.getMedicineId());
        Assertions.assertThat(medicineEntity.getName()).isEqualTo(medicine.getName());
    }

    private static void assertions(MedicineEntity medicineEntity, Medicine medicine) {
        assertEquals(medicineEntity.getMedicineId(), medicine.getMedicineId());
        assertEquals(medicineEntity.getName(), medicine.getName());
    }
}
