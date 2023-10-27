package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.api.IssueReceiptDTO;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class IssueReceiptDTOTest {

    @Test
    public void testIssueReceiptDTO() {
        //given
        IssueReceiptDTO issueReceiptDTO1 = new IssueReceiptDTO(
                List.of(MedicineExampleFixtures.medicineDTOExampleData1(),
                        MedicineExampleFixtures.medicineDTOExampleData1()),
                PatientExampleFixtures.patientExample1(),
                DoctorExampleFixtures.doctorExample1()
        );
        //when, then
        assertEquals(2, issueReceiptDTO1.getMedicineDTOList().size());
        assertions(issueReceiptDTO1);
        //given
        IssueReceiptDTO issueReceiptDTO2 = IssueReceiptDTO.builder()
                .medicineDTOList(List.of(MedicineExampleFixtures.medicineDTOExampleData1()))
                .patient(PatientExampleFixtures.patientExample1())
                .doctor(DoctorExampleFixtures.doctorExample1())
                .build();
        assertions(issueReceiptDTO2);


        IssueReceiptDTO issueReceiptDTO3 = new IssueReceiptDTO();
        assertNull(issueReceiptDTO3.getPatient());
        assertNull(issueReceiptDTO3.getDoctor());

        //given
        IssueReceiptDTO issueReceiptDTO4 = new IssueReceiptDTO(
                List.of(MedicineExampleFixtures.medicineDTOExampleData1()),
                PatientExampleFixtures.patientExample1(), null
        );
        //when,then
        assertEquals(1, issueReceiptDTO4.getMedicineDTOList().size());
        assertEquals("Jan", issueReceiptDTO4.getPatient().getName());
        assertEquals("j.kowalski@gmail.com", issueReceiptDTO4.getPatient().getEmail());
        assertNull(issueReceiptDTO4.getDoctor());
    }

    private static void assertions(IssueReceiptDTO issueReceiptDTO1) {
        assertEquals("Jan", issueReceiptDTO1.getPatient().getName());
        assertEquals("j.kowalski@gmail.com", issueReceiptDTO1.getPatient().getEmail());
        assertEquals("Jan", issueReceiptDTO1.getDoctor().getName());
        assertEquals("j.kowalski@gmail.com", issueReceiptDTO1.getDoctor().getEmail());
    }
}
