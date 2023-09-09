package com.HealthMeetProject.code.api.controller;


import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MedicineJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.*;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientController {
    private final MeetingRequestService meetingRequestService;
    private final NoteJpaRepository noteJpaRepository;
    private final PatientService patientService;
    private final ReceiptJpaRepository receiptJpaRepository;
    private final MedicineJpaRepository medicineJpaRepository;
    public static final String PATIENT_HISTORY = "/patient/visit_history";
    public static final String PATIENT = "/patient";

    @GetMapping(PATIENT_HISTORY)
    public String getPatientHistoryPage(Model model) {
        String email = patientService.authenticate();
        List<MeetingRequest> allCompletedServiceRequestsByEmail = meetingRequestService.findAllCompletedServiceRequestsByEmail(email);
        List<MedicineEntity> medicines = medicineJpaRepository.findByPatientEmail(email);

        BigDecimal totalCosts = sumTotalCosts(allCompletedServiceRequestsByEmail, medicines);

        List<NoteEntity> byPatientEmail = noteJpaRepository.findByPatientEmail(email);
        List<ReceiptEntity> receipts = receiptJpaRepository.findPatientReceipts(email);

        Optional<Integer> maxReceiptId = receipts.stream()
                .map(ReceiptEntity::getReceiptId)
                .max(Integer::compareTo);
        List<MedicineEntity> medicinesFromLastVisit;
        if(maxReceiptId.isPresent()){
            medicinesFromLastVisit= medicineJpaRepository.findByReceipt(maxReceiptId.get());
        }else{
            medicinesFromLastVisit = new ArrayList<>();
        }
        model.addAttribute("allCompletedServiceRequestsByEmail", allCompletedServiceRequestsByEmail);
        model.addAttribute("byPatientEmail", byPatientEmail);
        model.addAttribute("totalCosts", totalCosts);
        model.addAttribute("receipts", receipts);
        model.addAttribute("medicinesFromLastVisit", medicinesFromLastVisit);

        return "patient_history";
    }

    private  BigDecimal sumTotalCosts(List<MeetingRequest> allCompletedServiceRequestsByEmail, List<MedicineEntity> medicines) {
        BigDecimal totalCostsMeetings = allCompletedServiceRequestsByEmail.stream()
                .map(request -> request.getDoctor().getEarningsPerVisit())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCostsMedicines = medicines.stream()
                .map(MedicineEntity::getApproxPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalCostsMedicines.add(totalCostsMeetings);
    }
}