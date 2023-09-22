package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(DoctorApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorApiController {
    public static final String BASE_PATH = "/api/doctors";
    public static final String DOCTOR_ID = "/%s";
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final DoctorDAO doctorDAO;

    @GetMapping()
    public DoctorDTOs getAllDoctors() {
        return doctorDAO.findAllDoctors();
    }

    @GetMapping("/{doctorId}")
    public DoctorDTO getDoctorDetails(@PathVariable Integer doctorId) {
       return doctorService.findById(doctorId).map(doctorMapper::mapToDTO).orElseThrow(
                () -> new NotFoundException("Doctor with given email does not exist"));
    }

    @GetMapping("terms/{doctorId}")
    public AvailabilityScheduleDTOs getDoctorAvailableTerms(@PathVariable Integer doctorId) {
        DoctorDTO doctorDTO = doctorService.findById(doctorId).map(doctorMapper::mapToDTO).orElseThrow(
                () -> new NotFoundException("Doctor with given email does not exist"));

        return AvailabilityScheduleDTOs.of(availabilityScheduleService
                .findAllAvailableTermsByGivenDoctor(doctorDTO.getEmail()));
    }

    @PatchMapping("/{doctorId}")
    @Transactional
    public ResponseEntity<?> updateDoctor(
            @PathVariable Integer doctorId,
            @Valid @RequestBody DoctorDTO updatedDoctorDTO
    ) {
        Doctor existingDoctor = doctorService.findById(doctorId).orElse(null);
        if (existingDoctor == null) {
            return ResponseEntity.noContent().build();
        }

        doctorService.conditionsToUpdate(updatedDoctorDTO, existingDoctor);
        doctorDAO.save(existingDoctor);

        return ResponseEntity.ok(existingDoctor);
    }
    @PostMapping
    public ResponseEntity<DoctorDTO> registerDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        doctorService.register(doctorDTO);
        return ResponseEntity
                .created(URI.create(BASE_PATH + DOCTOR_ID.formatted(doctorDTO.getDoctorId())))
                .build();
    }
    @GetMapping("/{id}")
    public DoctorDTO getPatientById(@PathVariable Integer id) {
        //noinspection OptionalGetWithoutIsPresent
        return doctorMapper.mapToDTO(doctorDAO.findById(id).get());

    }

}
