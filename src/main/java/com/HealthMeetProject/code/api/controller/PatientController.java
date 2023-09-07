package com.HealthMeetProject.code.api.controller;


import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientController {
    private final MeetingRequestService meetingRequestService;
    private final NoteJpaRepository noteJpaRepository;
    private final PatientService patientService;
    public static final String PATIENT_HISTORY = "/patient/visit_history";
    public static final String PATIENT = "/patient";

    @GetMapping(PATIENT_HISTORY)
    public String getPatientHistoryPage(Model model){
        String email = patientService.authenticate();
        List<MeetingRequest> allCompletedServiceRequestsByEmail = meetingRequestService.findAllCompletedServiceRequestsByEmail(email);
        List<NoteEntity> byPatientEmail = noteJpaRepository.findByPatientEmail(email);
        BigDecimal totalCosts = allCompletedServiceRequestsByEmail.stream()
                .map(request -> request.getDoctor().getEarningsPerVisit())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("allCompletedServiceRequestsByEmail", allCompletedServiceRequestsByEmail);
        model.addAttribute("byPatientEmail", byPatientEmail);
        model.addAttribute("totalCosts", totalCosts);


        return "patient_history";
    }

}