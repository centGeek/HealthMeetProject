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

import java.net.URI;

@RestController
@RequestMapping()
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientRegistrationApiController {
    public static final String BASE_PATH = "/api/patients";
    public static final String PATIENT_ID = "/%s";
    private final PatientService patientService;
    private final PatientDAO patientDAO;
    private final PatientMapper patientMapper;


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


}
