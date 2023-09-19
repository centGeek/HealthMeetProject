package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientRegistrationApiController {

    private final PatientService patientService;
    private final PatientDAO patientDAO;
    private final PatientMapper patientMapper;


    @PostMapping()
    public ResponseEntity<?> registerPatient(@RequestBody @Valid PatientDTO patientDTO) {
        try {
            patientService.register(patientDTO);
            return ResponseEntity.ok(patientDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An account already exists for this email or phone.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Integer id) {
        try {
            Patient byId = patientDAO.findById(id);
            PatientDTO patientDTO = patientMapper.mapToDTO(byId);
            return ResponseEntity.ok(patientDTO);
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }


}
