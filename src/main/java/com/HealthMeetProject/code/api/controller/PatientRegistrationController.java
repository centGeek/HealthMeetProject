package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Pattern;

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
    public String userRegistration(final @Valid PatientDTO patientDTO, final BindingResult bindingResult, final Model model,
                                   HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", patientDTO);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            if(!Pattern.matches("^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$", patientDTO.getPhone())){
                model.addAttribute("errorMessage",
                        "Your phone: [%s] needs to fit to pattern: [+xx xxx xxx xxx]".formatted(patientDTO.getPhone()));
                return "error";
            }
            if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", patientDTO.getPhone())){
                model.addAttribute("errorMessage",
                        "Your email: [%s] needs to fit real email pattern".formatted(patientDTO.getEmail()));
                return "error";
            }
            model.addAttribute("patientDTO", patientDTO);
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