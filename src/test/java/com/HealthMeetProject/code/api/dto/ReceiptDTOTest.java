package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.ReceiptDTO;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptDTOTest {

    @Test
    public void testGetAndSetNow() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setDoctorDTO(DoctorDTOFixtures.getDoctorDTO1());
        receiptDTO.setMeetingId(1);
        receiptDTO.setPatientDTO(PatientDTOFixtures.patientDTOExample1());
        receiptDTO.setNow("2023-10-24 15:30:00");
        assertEquals("2023-10-24 15:30:00", receiptDTO.getNow());
        assertEquals(receiptDTO.getPatientDTO(), PatientDTOFixtures.patientDTOExample1());
        assertEquals(receiptDTO.getPatientDTO(), PatientDTOFixtures.patientDTOExample1());
    }
}
