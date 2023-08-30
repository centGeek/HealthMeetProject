package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.business.UserService;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class RegistrationController {
    private UserService userService;
    public static final String REGISTER = "/register";

    @GetMapping(REGISTER)
    public String register(final Model model) {
        model.addAttribute("userData", new UserEntity());
        return "account/register";
    }

    @PostMapping("/add")
    public String addEmployee(
            @Valid @ModelAttribute ("UserData")UserData userData
            ) throws UserAlreadyExistsException {
        UserData newUser = UserData.builder()
                .name(userData.getName())
                .surname(userData.getSurname())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .roles(userData.getRoles())
                .build();
        userService.register(newUser);
        return "redirect:register";
    }

}
