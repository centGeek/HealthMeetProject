package com.HealthMeetProject.code.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    public static final String HOME = "/";

    public static final String ABOUT = "/about";

    @RequestMapping(HOME)
    public String Home(){
        return "home";
    }

    @RequestMapping(ABOUT)
    public String About(){
        return "about";
    }


}

