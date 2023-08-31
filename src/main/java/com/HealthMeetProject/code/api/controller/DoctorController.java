package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class DoctorController {
    public static final String PATIENT = "/patient";
    public final DoctorService doctorService;
    private final DoctorMapper doctorMapper;


    @GetMapping(value = PATIENT)
    public String doctorsPage(Model model) {
        List<DoctorDTO> allAvailableDoctors = doctorService.findAllAvailableDoctors().stream()
                .map(doctorMapper::map).toList();
            model.addAttribute("allAvailableDoctors", allAvailableDoctors);
            return "patient";
    }
}
