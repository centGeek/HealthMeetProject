package com.HealthMeetProject.code.api.controller.rest;

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

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteApiController {

    private final MeetingRequestService meetingRequestService;
    private final DoctorService doctorService;
    private final NoteDAO noteDAO;

    @GetMapping("/{meetingId}")
    public ResponseEntity<?> getNote(
            @PathVariable Integer meetingId
    ) {
        MeetingRequest meetingRequestEntity = meetingRequestService.findById(meetingId);
        Doctor doctor = meetingRequestEntity.getDoctor();
        Patient patient = meetingRequestEntity.getPatient();
        OffsetDateTime visitEnd = meetingRequestEntity.getVisitEnd();
        OffsetDateTime visitStart = meetingRequestEntity.getVisitStart();

        Note noteDTO = new Note();
        noteDTO.setPatient(patient);
        noteDTO.setNoteId(meetingId);
        noteDTO.setDoctor(doctor);
        noteDTO.setStartTime(visitStart);
        noteDTO.setEndTime(visitEnd);

        return ResponseEntity.ok(noteDTO);
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
    public ResponseEntity<?> getIllnessHistory(
            @PathVariable Integer meetingId
    ) {
        MeetingRequest byId = meetingRequestService.findById(meetingId);
        Patient patient = byId.getPatient();
        List<Note> byPatientEmail = noteDAO.findByPatientEmail(patient.getEmail());
        if (byPatientEmail.isEmpty()){
            ResponseEntity.noContent();
        }
        return ResponseEntity.ok(byPatientEmail);
    }
}
