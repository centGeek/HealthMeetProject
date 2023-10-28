package com.HealthMeetProject.code.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomLoginController {
    public static final String LOGGING = "/login";

    @GetMapping(LOGGING)
    public String customDoctorLoginForm() {
        return "custom-login";
    }

}
