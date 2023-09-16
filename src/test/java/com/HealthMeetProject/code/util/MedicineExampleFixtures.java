package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;

import java.math.BigDecimal;

public class MedicineExampleFixtures {
    public static MedicineEntity medicineEntityExampleData1() {
        return MedicineEntity.builder()
                .name("Morfina")
                .quantity(3)
                .approxPrice(BigDecimal.valueOf(500))
                .receipt(ReceiptExampleFixtures.receiptEntityExampleData1())
                .build();
    }
    public static Medicine medicineExampleData1() {
        return Medicine.builder()
                .name("Morfina")
                .quantity(3)
                .approxPrice(BigDecimal.valueOf(500))
                .receipt(ReceiptExampleFixtures.receiptExampleData1())
                .build();
    }

    public static MedicineDTO medicineDTOExampleData1() {
        return MedicineDTO.builder()
                .name("Morfina")
                .quantity(3)
                .approxPrice(BigDecimal.valueOf(500))
                .build();
    }

    public static MedicineEntity medicineEntityExampleData2() {
        return MedicineEntity.builder()
                .name("Apap na noc")
                .quantity(30)
                .approxPrice(BigDecimal.valueOf(30))
                .receipt(ReceiptExampleFixtures.receiptEntityExampleData1())
                .build();
    }
    public static Medicine medicineExampleData2() {
        return Medicine.builder()
                .name("Apap na noc")
                .quantity(30)
                .approxPrice(BigDecimal.valueOf(30))
                .receipt(ReceiptExampleFixtures.receiptExampleData1())
                .build();
    } public static MedicineEntity medicineEntityExampleData3() {
        return MedicineEntity.builder()
                .name("ibuprom")
                .quantity(2)
                .approxPrice(BigDecimal.valueOf(31))
                .receipt(ReceiptExampleFixtures.receiptEntityExampleData1())
                .build();
    }

}