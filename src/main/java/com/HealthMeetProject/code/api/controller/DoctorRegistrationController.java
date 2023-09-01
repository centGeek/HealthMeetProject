package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class DoctorRegistrationController {


    private DoctorService doctorService;
    public static final String DOCTOR_REGISTER = "/doctor_register";


    @GetMapping(DOCTOR_REGISTER)
    public String register(final Model model) {
        model.addAttribute("userData", DoctorDTO.builder().userData(UserData.builder().active(true).build()).build());
        return "doctor_register";
    }

    @PostMapping(DOCTOR_REGISTER+"/add")
    public String userRegistration(final @Valid DoctorDTO doctorDTO, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", doctorDTO);
            return "doctor_register";
        }
        try {
            doctorService.register(doctorDTO);
        } catch (UserAlreadyExistsException e) {
            bindingResult.rejectValue("email", "doctorDTO.email", "An account already exists for this email.");
            model.addAttribute("registrationForm", doctorDTO);
            return "doctor_register";
        }

        model.addAttribute("doctorDTO", doctorDTO);

        return "doctor_register_successfully";
    }

}