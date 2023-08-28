package com.HealthMeetProject.code.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    public static final String HOME = "/";

    @RequestMapping(HOME)
    public String Home(){
        return "home";
    }
}
