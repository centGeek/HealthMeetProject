package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.business.PatientService;
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
public class PatientRegistrationController {


    private PatientService patientService;
    public static final String PATIENT_REGISTER = "/patient_register";


    @GetMapping(PATIENT_REGISTER)
    public String register(final Model model) {
        model.addAttribute("userData", PatientDTO.builder().user(UserData.builder().active(true).build()).build());
        return "patient_register";
    }

    @PostMapping(PATIENT_REGISTER+"/add")
    public String userRegistration(final @Valid PatientDTO patientDTO, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", patientDTO);
            return "patient_register";
        }
        try {
            patientService.register(patientDTO);
        } catch (UserAlreadyExistsException e) {
            bindingResult.rejectValue("email", "patientDTO.email", "An account already exists for this email.");
            bindingResult.rejectValue("phone", "patientDTO.phone", "An account already exists for this phone.");
            model.addAttribute("registrationForm", patientDTO);
            return "patient_register";
        }

        model.addAttribute("patientDTO", patientDTO);

        return "patient_register_successfully";
    }

}