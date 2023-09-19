package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/availability-schedule")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AvailabilityScheduleApiController {

    private final DoctorService doctorService;
    private final DoctorEntityMapper doctorEntityMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final AvailabilityScheduleDAO availabilityScheduleDAO;

    @GetMapping()
    public ResponseEntity<?> getAllAvailableTerms() {
        List<AvailabilityScheduleDTO> availabilityScheduleDTOList = availabilityScheduleDAO.findAll()
                .stream()
                .sorted(Comparator.comparing(AvailabilityScheduleDTO::getSince))
                .collect(Collectors.toList());

        return ResponseEntity.ok(availabilityScheduleDTOList);
    }
    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDoctorAvailableTerms(@PathVariable Integer doctorId) {
        Optional<Doctor> doctor = doctorService.findById(doctorId); // Implement findById method in DoctorService
        if (doctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        List<AvailabilityScheduleDTO> doctorTermsSorted = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctor.get().getEmail())
                .stream()
                .sorted(Comparator.comparing(AvailabilityScheduleDTO::getSince))
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorTermsSorted);
    }

    @PostMapping("/{doctorId}")
    public ResponseEntity<?> addTerms(
            @PathVariable Integer doctorId,
            @RequestParam("since") String since,
            @RequestParam("toWhen") String toWhen
    ) {
        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        OffsetDateTime sinceOffsetDateTime = availabilityScheduleService.parseToOffsetDateTime(since, zoneId);
        OffsetDateTime whenOffsetDateTime = availabilityScheduleService.parseToOffsetDateTime(toWhen, zoneId);

        Optional<Doctor> doctor = doctorService.findById(doctorId);
        if (doctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor.get());
        availabilityScheduleService.addTerm(sinceOffsetDateTime, whenOffsetDateTime, doctorEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{availabilityScheduleId}")
    public ResponseEntity<?> deleteTerm(
            @PathVariable Integer availabilityScheduleId
    ) {
        try {
            availabilityScheduleDAO.deleteById(availabilityScheduleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Problem with deleting occurred");
        }
    }
}
