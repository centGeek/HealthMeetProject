package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.business.PatientHistoryDTOService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientApiController {

    private final MeetingRequestDAO meetingRequestDAO;
    private final PatientHistoryDTOService patientHistoryDTOService;


    @GetMapping("/history")
    public ResponseEntity<?> getPatientHistory(@RequestParam String email) {
        PatientHistoryDTO patientHistoryDTO = patientHistoryDTOService.patientHistoryDTO(email);
        return ResponseEntity.ok(patientHistoryDTO);
    }

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
