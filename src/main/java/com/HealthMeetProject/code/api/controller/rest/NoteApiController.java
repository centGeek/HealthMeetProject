package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.IllnessHistoryDTOs;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping(NoteApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteApiController {
    public static final String BASE_PATH = "/api/notes";
    private final MeetingRequestService meetingRequestService;
    private final DoctorService doctorService;
    private final NoteDAO noteDAO;
    private final PatientDAO patientDAO;

    @GetMapping("/{meetingId}")
    public Note getNote(
            @PathVariable Integer meetingId,
            @RequestParam String illness

    ) {
        MeetingRequest meetingRequestEntity = meetingRequestService.restFindById(meetingId);
        Doctor doctor = meetingRequestEntity.getDoctor();
        Patient patient = meetingRequestEntity.getPatient();
        LocalDateTime visitEnd = meetingRequestEntity.getVisitEnd();
        LocalDateTime visitStart = meetingRequestEntity.getVisitStart();

        Note noteDTO = new Note();
        noteDTO.setIllness(illness);
        noteDTO.setPatient(patient);
        noteDTO.setNoteId(meetingId);
        noteDTO.setDescription(meetingRequestEntity.getDescription());
        noteDTO.setDoctor(doctor);
        noteDTO.setStartTime(visitStart);
        noteDTO.setEndTime(visitEnd);

        return noteDTO;
    }

    @PostMapping("/{meetingId}")
    public ResponseEntity<?> addNote(
            @PathVariable Integer meetingId,
            @RequestBody Note note
    ) {
        try {
            MeetingRequest meetingRequest = meetingRequestService.restFindById(meetingId);
            doctorService.writeNote(note.getDoctor(), note.getIllness(), note.getDescription(), note.getPatient(), meetingRequest.getVisitStart(), meetingRequest.getVisitEnd());
            return ResponseEntity
                    .created(URI.create(BASE_PATH + "/"+meetingId))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patient/{email}")
    public IllnessHistoryDTOs getIllnessHistory(
            @PathVariable String email
    ) {
        Patient patient = patientDAO.findByEmail(email);
        return IllnessHistoryDTOs.of(noteDAO.findByPatientEmail(patient.getEmail())
                .stream().map(Note::getIllness).toList());

    }
}
