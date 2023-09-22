package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.ReceiptApiController;
import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.ReceiptDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ReceiptApiControllerTest {

    @Mock
    private MeetingRequestDAO meetingRequestDAO;

    @Mock
    private ReceiptService receiptService;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private ReceiptApiController receiptApiController;


    @Test
    public void testGetReceiptPage() {
        // Arrange
        Integer meetingId = 1;
        MeetingRequest meetingRequest = new MeetingRequest();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        when(meetingRequestDAO.findById(meetingId)).thenReturn(meetingRequest);
        meetingRequest.setDoctor(doctor);
        meetingRequest.setPatient(patient);
        when(patientMapper.mapToDTO(patient)).thenReturn(PatientDTOFixtures.patientDTOExample1());
        when(doctorMapper.mapToDTO(doctor)).thenReturn(DoctorDTOFixtures.getDoctorDTO1());

        // Act
        ReceiptDTO receiptDTO = receiptApiController.getReceiptPage(meetingId);

        // Assert
        assertNotNull(receiptDTO);
        assertEquals(meetingId, receiptDTO.meetingId());
    }

    @Test
    public void testAddMedicine() {
        // Arrange
        String medicineName = "Medicine A";
        int quantity = 3;
        BigDecimal approxPrice = BigDecimal.valueOf(15.99);
        List<MedicineDTO> medicineList = new ArrayList<>();

        // Act
        ResponseEntity<?> responseEntity = receiptApiController.addMedicine(
                medicineName, quantity, approxPrice, medicineList);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertFalse(medicineList.isEmpty());
        MedicineDTO addedMedicine = medicineList.get(0);
        assertEquals(medicineName, addedMedicine.getName());
        assertEquals(quantity, addedMedicine.getQuantity());
        assertEquals(approxPrice, addedMedicine.getApproxPrice());
    }

    @Test
    public void testIssueReceipt_Success() {
        // Arrange
        Integer meetingId = 1;
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        Patient patient = PatientExampleFixtures.patientExample1();
        List<MedicineDTO> medicineList = new ArrayList<>();
        medicineList.add(MedicineExampleFixtures.medicineDTOExampleData1());
        when(meetingRequestDAO.findById(meetingId)).thenReturn(meetingRequest);
        meetingRequest.setPatient(patient);

        // Act
        ResponseEntity<?> responseEntity = receiptApiController.issueReceipt(meetingId, medicineList);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(receiptService, times(1)).issueReceipt(medicineList, patient);
    }

    @Test
    public void testIssueReceipt_NoMedicine() {
        // Arrange
        Integer meetingId = 1;
        MeetingRequest meetingRequest = new MeetingRequest();
        Patient patient = new Patient();
        List<MedicineDTO> medicineList = new ArrayList<>();
        when(meetingRequestDAO.findById(meetingId)).thenReturn(meetingRequest);
        meetingRequest.setPatient(patient);

        // Act and Assert
        assertThrows(ProcessingException.class,
                () -> receiptApiController.issueReceipt(meetingId, medicineList));
        verify(receiptService, never()).issueReceipt(medicineList, patient);
    }
}
