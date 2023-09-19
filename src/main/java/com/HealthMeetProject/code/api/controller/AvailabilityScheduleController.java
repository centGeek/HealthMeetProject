package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.HealthMeetProject.code.api.controller.DoctorController.DOCTOR;


@Controller
@AllArgsConstructor
public class AvailabilityScheduleController {

    public static final String ADD_TERMS = "/add/terms";
    public static final String DELETE_TERM = "/doctor/delete/term/{availability_schedule_id}";
    private final DoctorService doctorService;
    private final DoctorEntityMapper doctorEntityMapper;
    private final DoctorMapper doctorMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final AvailabilityScheduleDAO availabilityScheduleDAO;

    @GetMapping(DOCTOR)
    public String showYourAvailableTerms(Model model) {
        String email = doctorService.authenticateDoctor();
        DoctorDTO byEmail = doctorMapper.mapToDTO(doctorService.findByEmail(email));
        List<AvailabilityScheduleDTO> doctorTermsSorted = availabilityScheduleService.findAllTermsByGivenDoctor(byEmail.getEmail())
                .stream().sorted(Comparator.comparing(AvailabilityScheduleDTO::getSince)).toList();
        List<String> formattedSince = doctorTermsSorted.stream()
                .map(doctorTerm -> doctorTerm.getSince().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale.ENGLISH)))
                .collect(Collectors.toList());

        List<String> formattedToWhen = doctorTermsSorted.stream()
                .map(doctorTerm -> doctorTerm.getToWhen().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale.ENGLISH)))
                .collect(Collectors.toList());

        List<String> reservedTermStrings = doctorTermsSorted.stream()
                .map(schedule -> schedule.isAvailableDay() && schedule.isAvailableTerm() ? "free day" : "working day").toList();
        model.addAttribute("doctorTermsSorted", doctorTermsSorted);
        model.addAttribute("doctor", byEmail);
        model.addAttribute("formattedSince", formattedSince);
        model.addAttribute("formattedToWhen", formattedToWhen);
        model.addAttribute("reserved", reservedTermStrings);

        return "doctor";
    }


    @PostMapping(DOCTOR + ADD_TERMS)
    public String addTerms(@RequestParam("since") String since,
                           @RequestParam("toWhen") String toWhen) {
        LocalDateTime sinceLocalDateTime = availabilityScheduleService.parseToLocalDateTime(since);
        LocalDateTime whenLocalDateTime = availabilityScheduleService.parseToLocalDateTime(toWhen);

        String email = doctorService.authenticateDoctor();
        Doctor byEmail = doctorService.findByEmail(email);
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(byEmail);
        availabilityScheduleService.addTerm(sinceLocalDateTime, whenLocalDateTime, doctorEntity);
        return "redirect:/doctor";
    }




    @DeleteMapping(DELETE_TERM)
    public String deleteTerm(
            @PathVariable Integer availability_schedule_id
    ) {
        try {
            availabilityScheduleDAO.deleteById(availability_schedule_id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Problem with deleting occurred");
        }
        return "redirect:/doctor";
    }
}
