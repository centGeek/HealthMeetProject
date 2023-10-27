package com.HealthMeetProject.code.api.controller;


import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.business.PatientHistoryDTOService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientController {
    private final MeetingRequestDAO meetingRequestDAO;
    private final PatientService patientService;
    private final PatientDAO patientDAO;
    private final PatientHistoryDTOService patientHistoryDTOService;
    public static final String PATIENT_HISTORY = "/patient/visit_history";
    public static final String PATIENT = "/patient";

    @GetMapping(PATIENT_HISTORY)
    public String getPatientHistoryPage(Model model) {
        String authenticate = patientService.authenticate();
        patientHistoryDTOService.patientHistoryDTO(authenticate);
        PatientHistoryDTO patientHistoryDTO = patientHistoryDTOService.patientHistoryDTO(authenticate);
        model.addAttribute("patientHistoryDTO", patientHistoryDTO);

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
        return "home";
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
