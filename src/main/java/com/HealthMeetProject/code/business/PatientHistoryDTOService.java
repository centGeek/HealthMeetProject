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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientHistoryDTOService {
    private MeetingRequestDAO meetingRequestDAO;
    private MeetingRequestService meetingRequestService;
    private MedicineDAO medicineDAO;
    private ProcessingMoneyService processingMoneyService;
    private NoteJpaRepository noteJpaRepository;
    private ReceiptDAO receiptDAO;
    private ReceiptService receiptService;


    public PatientHistoryDTO patientHistoryDTO(String email) {
        List<MeetingRequest> allUpcomingVisits = meetingRequestDAO.findAllUpcomingVisitsByPatient(email);
        List<Boolean> canCancelMeetingList = meetingRequestService.canCancelMeetingList(allUpcomingVisits);
        List<MeetingRequest> allCompletedServiceRequestsByEmail = meetingRequestService.findAllCompletedServiceRequestsByEmail(email);
        List<MedicineEntity> medicines = medicineDAO.findByPatientEmail(email);
        BigDecimal totalCosts = processingMoneyService.sumTotalCosts(allCompletedServiceRequestsByEmail, medicines);
        List<NoteEntity> byPatientEmail = noteJpaRepository.findByPatientEmail(email);
        List<Receipt> receipts = receiptDAO.findPatientReceipts(email);
        List<MedicineEntity> medicinesFromLastVisit = receiptService.getMedicinesFromLastVisit(receipts);

        return PatientHistoryDTO.builder()
                .allUpcomingVisits(allUpcomingVisits)
                .canCancelMeetingList(canCancelMeetingList)
                .allCompletedServiceRequests(allCompletedServiceRequestsByEmail)
                .receipts(receipts)
                .totalCosts(totalCosts)
                .notes(byPatientEmail)
                .medicinesFromLastVisit(medicinesFromLastVisit)
                .build();
    }
}
