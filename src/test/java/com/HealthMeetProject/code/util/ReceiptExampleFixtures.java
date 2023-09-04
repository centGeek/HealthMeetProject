package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;

public class ReceiptExampleFixtures {
    public static ReceiptEntity receiptExampleData1() {
        return ReceiptEntity.builder().
                receiptNumber("32312312321312321").
                dateTime(DoctorExampleFixtures.availabilitySchedule1().getSince()).
                patient(PatientExampleFixtures.patientExample1()).
                doctor(DoctorExampleFixtures.doctorEntityExample1()).
                build();
    }

    public static ReceiptEntity receiptExampleData2() {
        return ReceiptEntity.builder().
                receiptNumber("32312256786345").
                dateTime(DoctorExampleFixtures.availabilitySchedule2().getSince()).
                patient(PatientExampleFixtures.patientExample1()).
                doctor(DoctorExampleFixtures.doctorEntityExample1()).
                build();
    }

    public static ReceiptEntity receiptExampleData3() {
        return ReceiptEntity.builder().
                receiptNumber("3232132344432451").
                dateTime(DoctorExampleFixtures.availabilitySchedule3().getSince()).
                patient(PatientExampleFixtures.patientExample2()).
                doctor(DoctorExampleFixtures.doctorEntityExample1()).
                build();
    }
}