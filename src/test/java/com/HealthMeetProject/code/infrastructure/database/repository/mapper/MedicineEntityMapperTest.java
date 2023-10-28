package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
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
        Receipt receipt = ReceiptExampleFixtures.receiptExampleData1();
        medicine.setReceipt(receipt);
        medicine.setApproxPrice(BigDecimal.TEN);
        medicine.setQuantity(3);
        receipt.setDoctor(DoctorExampleFixtures.doctorExample1());
        medicine.setReceipt(receipt);
        //when
        MedicineEntity medicineEntity = mapper.mapToEntity(medicine);

        // then
        assertions(medicineEntity, medicine);
        assertEquals(medicineEntity.getReceipt().getDateTime(),
                medicine.getReceipt().getDateTime());
        assertEquals(medicineEntity.getReceipt().getDoctor().getEmail(),
                medicine.getReceipt().getDoctor().getEmail());
        assertEquals(medicineEntity.getReceipt().getPatient().getEmail(),
                medicine.getReceipt().getPatient().getEmail());

        assertNotEquals(medicineEntity.hashCode(), medicine.hashCode());
    }

    @Test
    public void shouldMapMedicineEntityToMedicine() {
        // given
        MedicineEntity medicineEntity = new MedicineEntity();
        medicineEntity.setMedicineId(1);
        medicineEntity.setName("Ibuprofen");
        medicineEntity.setReceipt(ReceiptExampleFixtures.receiptEntityExampleData1());
        medicineEntity.getReceipt().setDoctor
                (DoctorExampleFixtures.doctorEntityExample1());
        medicineEntity.setApproxPrice(BigDecimal.TEN);
        medicineEntity.setQuantity(3);

        ReceiptEntity receiptEntity = ReceiptExampleFixtures.receiptEntityExampleData1();
        receiptEntity.setDoctor(DoctorExampleFixtures.doctorEntityExample1());
        medicineEntity.setReceipt(receiptEntity);

        //when
        Medicine medicine = mapper.mapFromEntity(medicineEntity);
        //then
        Assertions.assertThat(medicineEntity.getMedicineId()).isEqualTo(medicine.getMedicineId());
        Assertions.assertThat(medicineEntity.getName()).isEqualTo(medicine.getName());
        Assertions.assertThat(medicineEntity.getReceipt().getDoctor().getEarningsPerVisit())
                .isEqualTo(medicine.getReceipt().getDoctor().getEarningsPerVisit());
        Assertions.assertThat(medicineEntity.getReceipt().getDoctor().getEmail())
                .isEqualTo(medicine.getReceipt().getDoctor().getEmail());

    }

    private static void assertions(MedicineEntity medicineEntity, Medicine medicine) {
        assertEquals(medicineEntity.getMedicineId(), medicine.getMedicineId());
        assertEquals(medicineEntity.getName(), medicine.getName());
    }
}
