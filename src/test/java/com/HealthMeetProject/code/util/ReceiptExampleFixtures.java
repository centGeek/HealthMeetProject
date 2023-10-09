package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;

public class ReceiptExampleFixtures {
    public static ReceiptEntity receiptEntityExampleData1() {
        return ReceiptEntity.builder().
                receiptNumber("32312312321312321").
                dateTime(DoctorExampleFixtures.availabilityScheduleEntity1().getSince())
                .patient(PatientExampleFixtures.patientEntityExample1())
                .build();
    }

    public static Receipt receiptExampleData1() {
        return Receipt.builder().
                receiptNumber("32312312321312321")
                .dateTime(DoctorExampleFixtures.availabilityScheduleEntity1().getSince())
                .patient(PatientExampleFixtures.patientExample1())
                .build();
    }

    public static ReceiptEntity receiptEntityExampleData2() {
        return ReceiptEntity.builder().
                receiptNumber("32312256786345").
                dateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince()).
                build();
    }
    public static Receipt receiptEntityData2() {
        return Receipt.builder().
                receiptNumber("32312256786345").
                dateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince()).
                build();
    }

    public static ReceiptEntity receiptEntityExampleData3() {
        return ReceiptEntity.builder().
                receiptNumber("3232132344432451").
                dateTime(DoctorExampleFixtures.availabilityScheduleEntity3().getSince()).
                build();
    }
}