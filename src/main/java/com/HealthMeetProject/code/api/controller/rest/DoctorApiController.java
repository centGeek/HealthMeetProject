package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(DoctorApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorApiController {
    public static final String BASE_PATH = "/api/doctors";
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final DoctorDAO doctorDAO;
    @GetMapping()
    public DoctorDTOs getAllDoctors(){
        return doctorDAO.findAllDoctors();
    }
    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDoctorDetails(@PathVariable Integer doctorId) {
        Optional<Doctor> doctor = doctorService.findById(doctorId);
        if (doctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        DoctorDTO doctorDTO = doctorMapper.mapToDTO(doctor.get());
        return ResponseEntity.ok(doctorDTO);
    }

    @GetMapping("terms/{doctorId}")
    public ResponseEntity<?> getDoctorAvailableTerms(@PathVariable Integer doctorId) {
        Doctor doctor = doctorService.findById(doctorId).orElse(null);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        List<AvailabilityScheduleDTO> allTermsByGivenDoctor = availabilityScheduleService
                .findAllAvailableTermsByGivenDoctor(doctor.getEmail());
            return ResponseEntity.ok(allTermsByGivenDoctor);
    }
//    return ResponseEntity.created(URI.create(BASE_PATH + DOCTOR_ID_RESULT.formatted(allTermsByGivenDoctor.get()))).build();

    @PatchMapping("/{doctorId}")
    @Transactional
    public ResponseEntity<?> updateDoctor(
            @PathVariable Integer doctorId,
            @Valid @RequestBody DoctorDTO updatedDoctorDTO
    ) {
        Doctor existingDoctor = doctorService.findById(doctorId).orElse(null);
        if (existingDoctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        doctorService.conditionsToUpdate(updatedDoctorDTO, existingDoctor);
        doctorDAO.save(existingDoctor);

        return ResponseEntity.ok(existingDoctor);
    }
}
