package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.mapper.MedicineMapper;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import com.HealthMeetProject.code.util.ReceiptExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTest {

    @Mock
    private DoctorService doctorService;
    @Mock
    private DoctorDAO doctorDAO;
    @Mock
    private MeetingRequestService meetingRequestService;
    @Mock
    private MedicineEntityMapper medicineEntityMapper;
    @Mock
    private MedicineMapper medicineMapper;
    @Mock
    private MedicineDAO medicineDAO;
    @InjectMocks
    private ReceiptService receiptService;


    @Test
    void testIssueReceipt() {
        //given
        List<MedicineDTO> medicineList = new ArrayList<>();
        medicineList.add(MedicineExampleFixtures.medicineDTOExampleData1());

        Patient patient = PatientExampleFixtures.patientExample1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        //when
        when(doctorService.authenticateDoctor()).thenReturn(doctor.getEmail());
        when(doctorService.findByEmail(doctor.getEmail())).thenReturn(doctor);
        when(medicineMapper.mapFromDTO(MedicineExampleFixtures.medicineDTOExampleData1())).thenReturn(MedicineExampleFixtures.medicineExampleData1());
        when(medicineEntityMapper.mapToEntity(MedicineExampleFixtures.medicineExampleData1())).thenReturn(MedicineExampleFixtures.medicineEntityExampleData2());
        receiptService.issueReceipt(medicineList, patient);

        //then
        verify(doctorService, times(1)).authenticateDoctor();
        verify(doctorService, times(1)).findByEmail(doctor.getEmail());
        verify(doctorDAO, times(1)).issueReceipt(any(Receipt.class), anySet());
    }

    @Test
    void testBuildReceipt() {
        //given, when
        when(meetingRequestService.generateNumber(any(LocalDateTime.class))).thenReturn("2023.8.11-17.9.49.49");
        Patient patient = PatientExampleFixtures.patientExample1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        Receipt receipt = receiptService.buildReceipt(patient, doctor);
        //then
        assertEquals("2023.8.11-17.9.49.49", receipt.getReceiptNumber());
        assertEquals(receipt.getDateTime().toLocalDate(), OffsetDateTime.now().toLocalDate());
        assertEquals(receipt.getDoctor(), doctor);
        assertEquals(receipt.getPatient(), patient);

    }
    @Test
    public void testGetMedicinesFromLastVisit() {
        //given
        Receipt receipt1 = ReceiptExampleFixtures.receiptExampleData1();
        Receipt receipt2 = new Receipt();
        receipt2.setReceiptNumber("32312256786345");
        receipt2.setDateTime(DoctorExampleFixtures.availabilityScheduleEntity2().getSince());
        receipt2.setReceiptId(2);
        receipt2.setPatient(PatientExampleFixtures.patientExample1());

        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt1);
        receipts.add(receipt2);

        MedicineEntity medicine1 =MedicineExampleFixtures.medicineEntityExampleData1();
        MedicineEntity medicine2 =MedicineExampleFixtures.medicineEntityExampleData2();

        List<MedicineEntity> expectedMedicines = new ArrayList<>();
        expectedMedicines.add(medicine1);
        expectedMedicines.add(medicine2);
        //when
        when(receiptService.getMedicinesFromLastVisit(receipts)).thenReturn(expectedMedicines);

        List<MedicineEntity> actualMedicines = receiptService.getMedicinesFromLastVisit(receipts);

        //then
        assertEquals(expectedMedicines.size(), actualMedicines.size());
        assertEquals(expectedMedicines.get(0).getName(), actualMedicines.get(0).getName());
        assertEquals(expectedMedicines.get(0), actualMedicines.get(0));
        assertEquals(expectedMedicines.get(1).getName(), actualMedicines.get(1).getName());
    }
}