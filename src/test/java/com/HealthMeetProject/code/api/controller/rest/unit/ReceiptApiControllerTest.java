package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.ReceiptApiController;
import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.Receipts;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.ReceiptRepository;
import com.HealthMeetProject.code.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptApiControllerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ReceiptService receiptService;
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private ReceiptApiController receiptApiController;


    @Test
    public void testGetReceiptPage() {
        //given
        Integer meetingId = 0;
        MeetingRequest meetingRequest = new MeetingRequest();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        meetingRequest.setDoctor(doctor);
        meetingRequest.setPatient(patient);
        when(receiptRepository.findPatientReceipts(any())).thenReturn(List.of(ReceiptExampleFixtures.receiptExampleData1()));

        //when
        Receipts receipts = receiptApiController.getReceiptPage(patient.getEmail());

        //then
        assertNotNull(receipts.getReceipts().get(0));
        assertEquals(meetingId, receipts.getReceipts().get(0).getReceiptId());
    }


    @Test
    public void testIssueReceipt_Success() {
        //given
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        Patient patient = PatientExampleFixtures.patientExample1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();

        List<MedicineDTO> medicineList = new ArrayList<>();
        medicineList.add(MedicineExampleFixtures.medicineDTOExampleData1());
        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(patient);
        when(doctorRepository.findByEmail(patient.getEmail())).thenReturn(Optional.of(doctor));
        meetingRequest.setPatient(patient);

        //when
        ResponseEntity<?> responseEntity = receiptApiController.issueReceipt(patient.getEmail(), doctor.getEmail(), medicineList);

        //then
        //noinspection deprecation
        assertEquals(201, responseEntity.getStatusCodeValue());
        verify(receiptService, times(1)).restIssueReceipt(medicineList, patient, doctor);
    }

    @Test
    public void testIssueReceipt_NoMedicine() {
        //given
        MeetingRequest meetingRequest = new MeetingRequest();
        Patient patient = PatientExampleFixtures.patientExample1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        List<MedicineDTO> medicineList = new ArrayList<>();
        meetingRequest.setPatient(patient);

        //when, then
        assertThrows(ProcessingException.class,
                () -> receiptApiController.issueReceipt(patient.getEmail(), doctor.getEmail(), medicineList));
        verify(receiptService, never()).issueReceipt(medicineList, patient);
    }
}
