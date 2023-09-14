package com.HealthMeetProject.code.api.controller;


import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.ProcessingMoneyService;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientController {
    private final MeetingRequestService meetingRequestService;
    private final MeetingRequestDAO meetingRequestDAO;
    private final NoteJpaRepository noteJpaRepository;
    private final PatientService patientService;
    private final ReceiptService receiptService;
    private final PatientDAO patientDAO;
    private final ReceiptDAO receiptDAO;
    private final MedicineDAO medicineDAO;
    private final ProcessingMoneyService processingMoneyService;
    public static final String PATIENT_HISTORY = "/patient/visit_history";
    public static final String PATIENT = "/patient";

    @GetMapping(PATIENT_HISTORY)
    public String getPatientHistoryPage(Model model) {
        String email = patientService.authenticate();

        List<MeetingRequest> allUpcomingVisits = meetingRequestDAO.findAllUpcomingVisits(email);
        List<Boolean> canCancelMeetingList = meetingRequestService.canCancelMeetingList(allUpcomingVisits);

        List<MeetingRequest> allCompletedServiceRequestsByEmail = meetingRequestService.findAllCompletedServiceRequestsByEmail(email);
        List<MedicineEntity> medicines = medicineDAO.findByPatientEmail(email);

        BigDecimal totalCosts = processingMoneyService.sumTotalCosts(allCompletedServiceRequestsByEmail, medicines);

        List<NoteEntity> byPatientEmail = noteJpaRepository.findByPatientEmail(email);
        List<Receipt> receipts = receiptDAO.findPatientReceipts(email);

        List<MedicineEntity> medicinesFromLastVisit = receiptService.getMedicinesFromLastVisit(receipts);
        model.addAttribute("allUpcomingVisits", allUpcomingVisits);
        model.addAttribute("canCancelMeetingList", canCancelMeetingList);
        model.addAttribute("allCompletedServiceRequestsByEmail", allCompletedServiceRequestsByEmail);
        model.addAttribute("byPatientEmail", byPatientEmail);
        model.addAttribute("totalCosts", totalCosts);
        model.addAttribute("receipts", receipts);
        model.addAttribute("medicinesFromLastVisit", medicinesFromLastVisit);

        return "patient_history";
    }




    @DeleteMapping("/patient/delete/meeting/{meetingId}")
    public String deleteMeeting(
            @PathVariable Integer meetingId
    ) {
        try {
            meetingRequestDAO.deleteById(meetingId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Problem with deleting occurred");
        }
        return "patient_history";
    }
    @GetMapping("/patient/{patientId}/edit")
    public String showEditDoctorForm(
            @PathVariable Integer patientId,
            Model model) {

        Patient existingDoctor = patientDAO.findById(patientId);
        if (existingDoctor == null) {
            return "redirect:/patient";
        }

        model.addAttribute("patient", existingDoctor);
        return "edit-patient";
    }
    @PatchMapping("/patient/{patientId}/edit")
    public String updateDoctor(
            @PathVariable Integer patientId,
            @ModelAttribute("doctor") PatientDTO patientDTO,
            RedirectAttributes redirectAttributes) {

        Patient existingPatient = patientDAO.findById(patientId);

        patientService.conditionsToUpdate(patientDTO, existingPatient);
        patientDAO.savePatient(existingPatient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient data has been changed correctly");
        return "redirect:/logout";
    }
}
