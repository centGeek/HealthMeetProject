package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;

import java.math.BigDecimal;

public class MedicineExampleData {
    public static MedicineEntity medicineExampleData1() {
        return MedicineEntity.builder()
                .name("Morfina")
                .quantity(3)
                .approxPrice(BigDecimal.valueOf(500))
                .receipt(ReceiptExampleData.receiptExampleData1())
                .build();
    } public static MedicineEntity medicineExampleData2() {
        return MedicineEntity.builder()
                .name("Apap na noc")
                .quantity(30)
                .approxPrice(BigDecimal.valueOf(30))
                .receipt(ReceiptExampleData.receiptExampleData1())
                .build();
    } public static MedicineEntity medicineExampleData3() {
        return MedicineEntity.builder()
                .name("ibuprom")
                .quantity(2)
                .approxPrice(BigDecimal.valueOf(31))
                .receipt(ReceiptExampleData.receiptExampleData1())
                .build();
    }

}