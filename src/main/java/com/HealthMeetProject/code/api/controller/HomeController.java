package com.HealthMeetProject.code.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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

