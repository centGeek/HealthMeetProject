package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoteController {
    public static final String NOTE_PAGE = "/doctor/issue/note/{meetingId}";
    public static final String NOTE_PAGE_ADD = "/doctor/issue/note/add/{meetingId}";

    private final MeetingRequestService meetingRequestService;
    private final DoctorService doctorService;
    private final NoteDAO noteDAO;
    @GetMapping(NOTE_PAGE)
    public String getNotePage(
            @PathVariable Integer meetingId,
            Model model
    ) {
        MeetingRequest meetingRequestEntity = meetingRequestService.findById(meetingId);
        Doctor doctor = meetingRequestEntity.getDoctor();
        Patient patient = meetingRequestEntity.getPatient();
        OffsetDateTime visitEnd = meetingRequestEntity.getVisitEnd();
        OffsetDateTime visitStart = meetingRequestEntity.getVisitStart();
        String start = visitStart.format(MeetingProcessingController.FORMATTER);
        String end = visitEnd.format(MeetingProcessingController.FORMATTER);
        model.addAttribute("patient", patient);
        model.addAttribute("meetingId", meetingId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        return "note";
    }

    @PostMapping(NOTE_PAGE_ADD)
    public String addNote(
            @PathVariable Integer meetingId,
            @RequestParam("illness") String illness,
            @RequestParam("description") String description
    ) {
        MeetingRequest meetingRequest = meetingRequestService.findById(meetingId);
        Doctor doctor = meetingRequest.getDoctor();
        Patient patient = meetingRequest.getPatient();
        doctorService.writeNote(doctor, illness, description, patient, meetingRequest.getVisitStart(), meetingRequest.getVisitEnd());

        return "redirect:/doctor";

    }
    @GetMapping("/doctor/illness/history/{meetingId}")
    public String illnessHistory(
            @PathVariable Integer meetingId,
            Model model
    ){
        MeetingRequest byId = meetingRequestService.findById(meetingId);
        Patient patient = byId.getPatient();
        List<Note> byPatientEmail = noteDAO.findByPatientEmail(patient.getEmail());
        model.addAttribute("byPatientEmail", byPatientEmail);
        model.addAttribute("patientName", patient.getName());
        model.addAttribute("patientSurname", patient.getSurname());
        model.addAttribute("patientPesel", patient.getPesel());
        return "patient_illness_history";
    }
}
