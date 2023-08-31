package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
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

    @GetMapping("/register")
    public String register(final Model model){
        model.addAttribute("userData", new DoctorDTO());
        return "doctor_registry";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid DoctorDTO userData, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userData);
            return "doctor_registry";
        }
//        try {
////            doctorService.register(userData);
//        }catch (UserAlreadyExistsException e){
//            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
//            model.addAttribute("registrationForm", userData);
//            return "doctor_registry";
//        }
////        return REDIRECT+"/starter";
        return null;
    }
}