package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public static final String ADD_TERMS = "/add/terms";

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
        List<AvailabilitySchedule> allTermsByGivenDoctor = availabilityScheduleService.findAllTermsByGivenDoctor(doctor.getEmail());
        model.addAttribute("allTermsByGivenDoctor", allTermsByGivenDoctor);
        model.addAttribute("doctor", doctor);
        return "availability_terms";
    }

    @GetMapping(DOCTOR)
    public String showYourAvailableTerms(Model model) {
        String email = doctorService.authenticateDoctor();
        Doctor byEmail = doctorService.findByEmail(email);
        List<AvailabilitySchedule> doctorTerms = availabilityScheduleService.findAllTermsByGivenDoctor(byEmail.getEmail());
        model.addAttribute("doctorTerms", doctorTerms);
        return "doctor";
    }

    @PostMapping(DOCTOR+ADD_TERMS)
    public String addTerms(@RequestParam("since") String since,
                           @RequestParam("toWhen") String toWhen,
                           final Model model) {

        OffsetDateTime sinceDateTime = OffsetDateTime.parse(since, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
        OffsetDateTime toWhenDateTime = OffsetDateTime.parse(toWhen, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));

        String email = doctorService.authenticateDoctor();
        Doctor byEmail = doctorService.findByEmail(email);
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(byEmail);

        AvailabilityScheduleDTO term = availabilityScheduleService.addTerm(sinceDateTime, toWhenDateTime, doctorEntity);
        model.addAttribute("term", term);

        return "redirect:/doctor";
    }
}
