package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.ReceiptApiController;
import com.HealthMeetProject.code.api.dto.api.IssueReceiptDTO;
import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.api.Receipts;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import com.HealthMeetProject.code.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptApiControllerTest {

    @Mock
    private PatientDAO patientDAO;

    @Mock
    private MedicineDAO medicineDAO;

    @Mock
    private ReceiptService receiptService;
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private MedicineEntityMapper medicineEntityMapper;

    @Mock
    private ReceiptDAO receiptDAO;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private ReceiptApiController receiptApiController;


    @Test
    public void testGetReceiptPage() {
        //given
        Integer meetingId = 0;
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        Patient patient = PatientExampleFixtures.patientExample1();
        MedicineEntity medicine = MedicineExampleFixtures.medicineEntityExampleData1();
        meetingRequest.setDoctor(doctor);
        meetingRequest.setPatient(patient);
        when(receiptDAO.findPatientReceipts(any())).thenReturn(List.of(ReceiptExampleFixtures.receiptExampleData1()));
        when(medicineDAO.findByReceipt(any())).thenReturn(List.of(medicine));
        when(medicineEntityMapper.mapFromEntity(any())).thenReturn(MedicineExampleFixtures.medicineExampleData1());

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
        meetingRequest.setPatient(patient);
        IssueReceiptDTO issueReceiptDTO = IssueReceiptDTO.builder().patient(patient).doctor(doctor).medicineDTOList(medicineList).build();
        //when
        ResponseEntity<?> responseEntity = receiptApiController.issueReceipt(issueReceiptDTO);

        //then
        //noinspection deprecation
        assertEquals(201, responseEntity.getStatusCodeValue());
        verify(receiptService, times(1)).restIssueReceipt(issueReceiptDTO);
    }

    @Test
    public void testIssueReceipt_NoMedicine() {
        //given
        MeetingRequest meetingRequest = new MeetingRequest();
        Patient patient = PatientExampleFixtures.patientExample1();
        Doctor doctor = DoctorExampleFixtures.doctorExample1();
        List<MedicineDTO> medicineList = new ArrayList<>();
        meetingRequest.setPatient(patient);
        IssueReceiptDTO issueReceiptDTO = IssueReceiptDTO.builder().patient(patient).doctor(doctor).medicineDTOList(medicineList).build();


        //when, then
        assertThrows(ProcessingException.class,
                () -> receiptApiController.issueReceipt(issueReceiptDTO));
        verify(receiptService, never()).issueReceipt(medicineList, patient);
    }
}
