package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.NoteDTOs;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(NoteApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteApiController {
    public static final String BASE_PATH = "/api/notes";
    private final MeetingRequestService meetingRequestService;
    private final DoctorService doctorService;
    private final NoteDAO noteDAO;

    @GetMapping("/{meetingId}")
    public Note getNote(
            @PathVariable Integer meetingId
    ) {
        MeetingRequest meetingRequestEntity = meetingRequestService.findById(meetingId);
        Doctor doctor = meetingRequestEntity.getDoctor();
        Patient patient = meetingRequestEntity.getPatient();
        LocalDateTime visitEnd = meetingRequestEntity.getVisitEnd();
        LocalDateTime visitStart = meetingRequestEntity.getVisitStart();

        Note noteDTO = new Note();
        noteDTO.setPatient(patient);
        noteDTO.setNoteId(meetingId);
        noteDTO.setDoctor(doctor);
        noteDTO.setStartTime(visitStart);
        noteDTO.setEndTime(visitEnd);

        return noteDTO;
    }

    @PostMapping("/add/{meetingId}")
    public ResponseEntity<?> addNote(
            @PathVariable Integer meetingId,
            @RequestParam("illness") String illness,
            @RequestParam("description") String description
    ) {
        MeetingRequest meetingRequest = meetingRequestService.findById(meetingId);
        Doctor doctor = meetingRequest.getDoctor();
        Patient patient = meetingRequest.getPatient();
        doctorService.writeNote(doctor, illness, description, patient, meetingRequest.getVisitStart(), meetingRequest.getVisitEnd());

        return ResponseEntity.ok("Note added successfully");
    }

    @GetMapping("/patient/{meetingId}")
    public List<String>  getIllnessHistory(
            @PathVariable Integer meetingId
    ) {
        MeetingRequest byId = meetingRequestService.findById(meetingId);
        Patient patient = byId.getPatient();
       return noteDAO.findByPatientEmail(patient.getEmail()).stream().map(Note::getIllness).toList();

    }
}
