package com.HealthMeetProject.code.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@WebMvcTest(CustomLoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loggingWorks() throws Exception {
        // given, when, then
        mockMvc.perform(get(CustomLoginController.LOGGING))
                .andExpect(status().isOk())
                .andExpect(view().name("custom-login"));
    }

}