package com.HealthMeetProject.code.infrastructure.integration.controller;

import com.HealthMeetProject.code.infrastructure.integration.configuration.AbstractIT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeControllerIT extends AbstractIT {

    private final TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void thatHomePageRequiredSigningIn() {
        String url = String.format("http://localhost:%s%s", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Experience modern healthcare management");
    }

    @Test
    void thatPatientLoggingPageLoadedCorrectly(){
        String url = String.format("http://localhost:%s%s/patient", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Please sign in");
    }
    @Test
    void thatDoctorLoggingPageLoadedCorrectly(){
        String url = String.format("http://localhost:%s%s/doctor", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Please sign in");
    }
    @Test
    void thatDoctorRegistryPageLoadedCorrectly(){
        String url = String.format("http://localhost:%s%s/doctor_register", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Register Page");
        Assertions.assertThat(page).contains("Clinic");
    }
    @Test
    void thatPatientRegistryPageLoadedCorrectly(){
        String url = String.format("http://localhost:%s%s/patient_register", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Address");
    }
}
