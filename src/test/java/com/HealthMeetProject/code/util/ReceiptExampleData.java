package com.HealthMeetProject.code.util;

import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;

public class ReceiptExampleData {
    public static ReceiptEntity receiptExampleData1() {
        return ReceiptEntity.builder().
                receiptNumber("32312312321312321").
                dateTime(DoctorExampleData.availabilitySchedule1().getSince()).
                patient(PatientExampleData.patientExample1()).
                doctor(DoctorExampleData.doctorExample1()).
                build();
    }

    public static ReceiptEntity receiptExampleData2() {
        return ReceiptEntity.builder().
                receiptNumber("32312256786345").
                dateTime(DoctorExampleData.availabilitySchedule2().getSince()).
                patient(PatientExampleData.patientExample1()).
                doctor(DoctorExampleData.doctorExample1()).
                build();
    }

    public static ReceiptEntity receiptExampleData3() {
        return ReceiptEntity.builder().
                receiptNumber("3232132344432451").
                dateTime(DoctorExampleData.availabilitySchedule3().getSince()).
                patient(PatientExampleData.patientExample2()).
                doctor(DoctorExampleData.doctorExample1()).
                build();
    }
}