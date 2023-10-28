package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.business.DoctorService;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DoctorRegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorRegistrationControllerTest {
    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("unused")
    private DoctorService doctorService;


    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        String badEmail = "badEmail";
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap = DoctorDTO.buildDefaultData().asMap();
        parametersMap.put("phone", "+48 920 102 957");
        parametersMap.put("email", badEmail);
        parametersMap.forEach(parameters::add);

        // when, then
        mockMvc.perform(post(DoctorRegistrationController.DOCTOR_REGISTER + "/add").params(parameters))
                .andExpect(status().is4xxClientError())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", Matchers.containsString(badEmail)))
                .andExpect(view().name("error"));
    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        // given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap = DoctorDTO.buildDefaultData().asMap();
        parametersMap.put("phone", phone);
        parametersMap.forEach(parameters::add);

        // when, then
        if (correctPhone) {
            DoctorDTO doctorDTO = DoctorDTO.buildDefaultData();
            Mockito.when(doctorService.register(Mockito.any())).thenReturn(doctorDTO);
            mockMvc.perform(post(DoctorRegistrationController.DOCTOR_REGISTER + "/add").params(parameters))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("doctorDTO"))
                    .andExpect(view().name("doctor_register_successfully"));
        } else {
            mockMvc.perform(post(DoctorRegistrationController.DOCTOR_REGISTER + "/add").params(parameters))
                    .andExpect(status().is4xxClientError())
                    .andExpect(model().attributeExists("errorMessage"))
                    .andExpect(model().attribute("errorMessage", Matchers.containsString(phone)))
                    .andExpect(view().name("error"));
        }
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "555-5555-555"),
                Arguments.of(true, "+48 504 203 260")
        );
    }

}