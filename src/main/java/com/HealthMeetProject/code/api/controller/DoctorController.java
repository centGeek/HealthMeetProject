package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.HealthMeetProject.code.api.controller.PatientController.PATIENT;

@Controller
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorMapper doctorMapper;
    private final DoctorEntityMapper doctorEntityMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    public static final String DOCTOR = "/doctor";


    @GetMapping(value = PATIENT)
    public String doctorsPage(Model model) {
        List<DoctorDTO> allAvailableDoctors = doctorService.findAllAvailableDoctors().stream()
                .map(doctorMapper::map).toList();

        model.addAttribute("allAvailableDoctors", allAvailableDoctors);
        return "patient";
    }

    @GetMapping("/terms/{doctorId}")
    public String showEmployeeDetails(
            @PathVariable Integer doctorId,
            Model model
    ) {
        DoctorEntity doctor = doctorJpaRepository.findById(doctorId).orElseThrow(() -> new EntityNotFoundException(
                "Employee entity not found, employeeId: [%s]".formatted(doctorId)
        ));
        List<AvailabilityScheduleDTO> allTermsByGivenDoctor = availabilityScheduleService.findAllTermsByGivenDoctor(doctor.getEmail());
        model.addAttribute("allTermsByGivenDoctor", allTermsByGivenDoctor);
        model.addAttribute("doctor", doctor);
        return "availability_terms";
    }

}
