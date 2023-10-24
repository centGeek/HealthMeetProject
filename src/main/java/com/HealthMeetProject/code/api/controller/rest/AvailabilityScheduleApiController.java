package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AvailabilityScheduleApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AvailabilityScheduleApiController {
    public static final String BASE_PATH = "/api/availability-schedule";
    public static final String AVAILABILITY_ID = "/%s";
    private final DoctorService doctorService;
    private final DoctorEntityMapper doctorEntityMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final AvailabilityScheduleDAO availabilityScheduleDAO;

    @GetMapping
    public AvailabilityScheduleDTOs getAllAvailableWorkingDays() {
        return AvailabilityScheduleDTOs.of(availabilityScheduleDAO.findAll().stream()
                .sorted(Comparator.comparing(AvailabilityScheduleDTO::getSince))
                .filter(AvailabilityScheduleDTO::isAvailableDay)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{doctorId}")
    public AvailabilityScheduleDTOs getDoctorAvailableTerms(@PathVariable Integer doctorId) {
        Doctor doctor = doctorService.findById(doctorId).orElseThrow(() ->
                new NotFoundException("Doctor with given email does not exist"));
        List<AvailabilityScheduleDTO> doctorTermsSorted = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctor.getEmail())
                .stream()
                .sorted(Comparator.comparing(AvailabilityScheduleDTO::getSince))
                .collect(Collectors.toList());
        return AvailabilityScheduleDTOs.of(doctorTermsSorted);

    }

    @PostMapping
    public ResponseEntity<AvailabilityScheduleDTO> addTerms(
            @RequestBody AvailabilityScheduleDTO availabilityScheduleDTO
    ) {
        try {

            Doctor doctor = doctorService.findById(availabilityScheduleDTO.getDoctor().getDoctorId())
                    .orElseThrow(() -> new NotFoundException("Not found doctor"));

            DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
            availabilityScheduleService.addTerm(availabilityScheduleDTO.getSince(), availabilityScheduleDTO.getToWhen(), doctorEntity);

            return ResponseEntity
                    .created(URI.create(BASE_PATH + AVAILABILITY_ID.formatted(doctor.getDoctorId())))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{availabilityScheduleId}")
    public ResponseEntity<?> deleteTerm(
            @PathVariable Integer availabilityScheduleId
    ) {
        try {
            availabilityScheduleDAO.deleteById(availabilityScheduleId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
