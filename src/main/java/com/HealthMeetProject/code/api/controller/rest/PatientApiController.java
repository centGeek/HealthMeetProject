package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.PatientHistoryDTOService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(PatientApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientApiController {
    public static final String BASE_PATH = "/api/patients";
    private final MeetingRequestDAO meetingRequestDAO;
    private final PatientHistoryDTOService patientHistoryDTOService;
    private final PatientService patientService;
    private final PatientDAO patientDAO;
    private final PatientMapper patientMapper;
    public static final String PATIENT_ID = "/%s";


    @DeleteMapping("/meeting/{meetingId}")
    public ResponseEntity<?> deleteMeeting(
            @PathVariable Integer meetingId
    ) {
        try {
            meetingRequestDAO.deleteById(meetingId);
            return ResponseEntity.ok("Meeting deleted successfully");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Problem with deleting occurred");
        }
    }
    @PostMapping
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody @Valid Patient patient) {
        PatientDTO patientDTO = patientMapper.mapToDTO(patient);
        patientService.register(patientDTO);
        return ResponseEntity
                .created(URI.create(BASE_PATH + PATIENT_ID.formatted(patient.getPatientId())))
                .build();
    }

    @GetMapping("/{id}")
    public PatientDTO getPatientById(@PathVariable Integer id) {
        return  patientMapper.mapToDTO(patientDAO.findById(id));

    }
    @GetMapping("/by-email/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return  patientMapper.mapToDTO(patientDAO.findByEmail(email));

    }

//    @GetMapping("/{patientId}/edit")
//    public ResponseEntity<?> getEditPatientForm(
//            @PathVariable Integer patientId
//    ) {
//        Patient existingPatient = patientDAO.findById(patientId);
//
//        if (existingPatient == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        PatientDTO patientDTO = PatientDTO.builder()
//                .name(existingPatient.getName())
//                .surname(existingPatient.getSurname())
//                .build();
//
//        return ResponseEntity.ok(patientDTO);
//    }
//
//    @PatchMapping("/{patientId}/edit")
//    public ResponseEntity<?> updatePatient(
//            @PathVariable Integer patientId,
//            @RequestBody PatientDTO patientDTO
//    ) {
//        Patient existingPatient = patientDAO.findById(patientId);
//
//        if (existingPatient == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Patient patient = patientMapper.mapFromDTO(patientDTO);
//        patientDAO.savePatient(patient);
//
//        return ResponseEntity.ok("Patient data has been updated successfully");
//    }
}
