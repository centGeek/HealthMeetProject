package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
import com.HealthMeetProject.code.util.ReceiptExampleFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientHistoryDTOServiceTest {

    @Mock
    private MeetingRequestDAO meetingRequestDAO;
    @Mock
    private MeetingRequestService meetingRequestService;
    @Mock
    private MedicineDAO medicineDAO;
    @Mock
    private ProcessingMoneyService processingMoneyService;
    @Mock
    private NoteJpaRepository noteJpaRepository;
    @Mock
    private ReceiptService receiptService;
    @Mock
    private ReceiptDAO receiptDAO;


    @InjectMocks
    private PatientHistoryDTOService patientHistoryDTOService;


    @Test
    public void testPatientHistoryDTOWithUpcomingVisits() {
        // Given
        String email = "example@example.com";
        List<MeetingRequest> allUpcomingVisits = new ArrayList<>();
        allUpcomingVisits.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
        List<Boolean> canCancelMeetingList = List.of(true);
        List<MeetingRequest> allCompletedServiceRequestsByEmail =
                List.of(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
        List<MedicineEntity> medicines = List.of(MedicineExampleFixtures.medicineEntityExampleData1());
        BigDecimal totalCosts = BigDecimal.ZERO;
        List<NoteEntity> byPatientEmail = List.of(NoteExampleFixtures.noteEntityExample1());
        List<Receipt> receipts = List.of(ReceiptExampleFixtures.receiptExampleData1());
        List<MedicineEntity> medicinesFromLastVisit = List.of(MedicineExampleFixtures.medicineEntityExampleData2());

        when(meetingRequestDAO.findAllUpcomingVisitsByPatient(email)).thenReturn(allUpcomingVisits);
        when(meetingRequestService.canCancelMeetingList(allUpcomingVisits)).thenReturn(canCancelMeetingList);
        when(meetingRequestService.findAllCompletedServiceRequestsByEmail(email)).thenReturn(allCompletedServiceRequestsByEmail);
        when(medicineDAO.findByPatientEmail(email)).thenReturn(medicines);
        when(processingMoneyService.sumTotalCosts(allCompletedServiceRequestsByEmail, medicines)).thenReturn(totalCosts);
        when(noteJpaRepository.findByPatientEmail(email)).thenReturn(byPatientEmail);
        when(receiptDAO.findPatientReceipts(email)).thenReturn(receipts);
        when(receiptService.getMedicinesFromLastVisit(receipts)).thenReturn(medicinesFromLastVisit);

        // When
        PatientHistoryDTO result = patientHistoryDTOService.patientHistoryDTO(email);

        // Then
        Assertions.assertEquals(result.getReceipts().get(0).getDateTime(), receipts.get(0).getDateTime());
        Assertions.assertEquals(result.getReceipts().get(0).getReceiptNumber(), receipts.get(0).getReceiptNumber());
    }

    @Test
    public void testNoArgsConstructor() {
        PatientHistoryDTO patientHistoryDTO = new PatientHistoryDTO();
        Assertions.assertNotNull(patientHistoryDTO);
        Assertions.assertNull(patientHistoryDTO.getAllUpcomingVisits());
        Assertions.assertNull(patientHistoryDTO.getCanCancelMeetingList());
        Assertions.assertNull(patientHistoryDTO.getAllCompletedServiceRequests());
        Assertions.assertNull(patientHistoryDTO.getNotes());
        Assertions.assertNull(patientHistoryDTO.getTotalCosts());
        Assertions.assertNull(patientHistoryDTO.getReceipts());
        Assertions.assertNull(patientHistoryDTO.getMedicinesFromLastVisit());
    }

    @Test
    public void testAllArgsConstructor() {
        List<MeetingRequest> upcomingVisits = new ArrayList<>();
        upcomingVisits.add(new MeetingRequest());
        List<Boolean> canCancelMeetingList = new ArrayList<>();
        canCancelMeetingList.add(true);
        List<MeetingRequest> completedServiceRequests = new ArrayList<>();
        completedServiceRequests.add(new MeetingRequest());
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity());
        BigDecimal totalCosts = new BigDecimal("100.50");
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt());
        List<MedicineEntity> medicinesFromLastVisit = new ArrayList<>();
        medicinesFromLastVisit.add(new MedicineEntity());

        PatientHistoryDTO patientHistoryDTO = new PatientHistoryDTO(
                upcomingVisits, canCancelMeetingList, completedServiceRequests,
                notes, totalCosts, receipts, medicinesFromLastVisit);

        Assertions.assertNotNull(patientHistoryDTO);
        Assertions.assertEquals(upcomingVisits, patientHistoryDTO.getAllUpcomingVisits());
        Assertions.assertEquals(canCancelMeetingList, patientHistoryDTO.getCanCancelMeetingList());
        Assertions.assertEquals(completedServiceRequests, patientHistoryDTO.getAllCompletedServiceRequests());
        Assertions.assertEquals(notes, patientHistoryDTO.getNotes());
        Assertions.assertEquals(totalCosts, patientHistoryDTO.getTotalCosts());
        Assertions.assertEquals(receipts, patientHistoryDTO.getReceipts());
        Assertions.assertEquals(medicinesFromLastVisit, patientHistoryDTO.getMedicinesFromLastVisit());
    }

    @Test
    public void testBuilder() {
        List<MeetingRequest> upcomingVisits = new ArrayList<>();
        upcomingVisits.add(new MeetingRequest());
        List<Boolean> canCancelMeetingList = new ArrayList<>();
        canCancelMeetingList.add(true);
        List<MeetingRequest> completedServiceRequests = new ArrayList<>();
        completedServiceRequests.add(new MeetingRequest());
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity());
        BigDecimal totalCosts = new BigDecimal("100.50");
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt());
        List<MedicineEntity> medicinesFromLastVisit = new ArrayList<>();
        medicinesFromLastVisit.add(new MedicineEntity());

        PatientHistoryDTO patientHistoryDTO = PatientHistoryDTO.builder()
                .allUpcomingVisits(upcomingVisits)
                .canCancelMeetingList(canCancelMeetingList)
                .allCompletedServiceRequests(completedServiceRequests)
                .notes(notes)
                .totalCosts(totalCosts)
                .receipts(receipts)
                .medicinesFromLastVisit(medicinesFromLastVisit)
                .build();

        Assertions.assertNotNull(patientHistoryDTO);
        Assertions.assertEquals(upcomingVisits, patientHistoryDTO.getAllUpcomingVisits());
        Assertions.assertEquals(canCancelMeetingList, patientHistoryDTO.getCanCancelMeetingList());
        Assertions.assertEquals(completedServiceRequests, patientHistoryDTO.getAllCompletedServiceRequests());
        Assertions.assertEquals(notes, patientHistoryDTO.getNotes());
        Assertions.assertEquals(totalCosts, patientHistoryDTO.getTotalCosts());
        Assertions.assertEquals(receipts, patientHistoryDTO.getReceipts());
        Assertions.assertEquals(medicinesFromLastVisit, patientHistoryDTO.getMedicinesFromLastVisit());
    }

    @Test
    public void testEqualsAndHashCode() {
        List<MeetingRequest> upcomingVisits1 = new ArrayList<>();
        upcomingVisits1.add(new MeetingRequest());
        List<MeetingRequest> upcomingVisits2 = new ArrayList<>();
        upcomingVisits2.add(new MeetingRequest());

        PatientHistoryDTO patientHistoryDTO1 = PatientHistoryDTO.builder()
                .allUpcomingVisits(upcomingVisits1)
                .build();

        PatientHistoryDTO patientHistoryDTO2 = PatientHistoryDTO.builder()
                .allUpcomingVisits(upcomingVisits2)
                .build();

        Assertions.assertEquals(patientHistoryDTO1, patientHistoryDTO2);
        Assertions.assertEquals(patientHistoryDTO1.hashCode(), patientHistoryDTO2.hashCode());
    }

}
