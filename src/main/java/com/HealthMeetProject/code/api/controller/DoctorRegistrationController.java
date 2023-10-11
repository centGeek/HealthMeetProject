package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Pattern;

@Controller
@AllArgsConstructor
public class DoctorRegistrationController {


    private DoctorService doctorService;
    public static final String DOCTOR_REGISTER = "/doctor_register";


    @GetMapping(DOCTOR_REGISTER)
    public String register(final Model model) {
        model.addAttribute("userData", DoctorDTO.builder().user(UserData.builder().active(true).build()).build());
        return "doctor_register";
    }

    @PostMapping(DOCTOR_REGISTER+"/add")
    public String userRegistration(final @Valid DoctorDTO doctorDTO,
                                   final BindingResult bindingResult, ModelMap model, HttpServletResponse response) {
        String error = handleMistakes(doctorDTO, bindingResult, model, response);
        if (error != null) return error;
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

    private String handleMistakes(DoctorDTO doctorDTO, BindingResult bindingResult, ModelMap model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            if(!Pattern.matches("^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$", doctorDTO.getPhone())){
                model.addAttribute("errorMessage",
                        "Your phone: [%s] needs to fit to pattern: [+xx xxx xxx xxx]".formatted(doctorDTO.getPhone()));
                return "error";
            }
            if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", doctorDTO.getPhone())){
                model.addAttribute("errorMessage",
                        "Your email: [%s] needs to fit real email pattern".formatted(doctorDTO.getEmail()));
                return "error";
            }


        }
        return null;
    }

}