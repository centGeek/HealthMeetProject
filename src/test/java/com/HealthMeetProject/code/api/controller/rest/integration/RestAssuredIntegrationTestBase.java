package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.AuthenticationTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.ControllerTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class RestAssuredIntegrationTestBase
        extends AbstractIntegrationTest
        implements ControllerTestSupport, AuthenticationTestSupport {
    @LocalServerPort
    private int serverPort;
    @Value("${server.servlet.context-path}")
    private String basePath;

    private String jSessionIdValue;

    @Autowired
    @SuppressWarnings("unused")
    private ObjectMapper objectMapper;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Test
    void contextLoaded() {
        assertThat(true).isTrue();
    }


    @BeforeEach
    void beforeEach() {
        jSessionIdValue = login("rest_api@wp.pl", "test")
                .and()
                .cookie("JSESSIONID")
                .header(HttpHeaders.LOCATION, "http://localhost:%s%s/".formatted(serverPort, basePath))
                .extract()
                .cookie("JSESSIONID");
    }


    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", jSessionIdValue);
    }

    public RequestSpecification requestSpecificationNoAuthentication() {
        return restAssuredBase();
    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(serverPort);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}