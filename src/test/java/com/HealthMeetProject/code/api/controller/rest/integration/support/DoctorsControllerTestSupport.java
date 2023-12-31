package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.DoctorApiController;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.api.DoctorDTOs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface DoctorsControllerTestSupport {
    RequestSpecification requestSpecification();

    default DoctorDTOs listDoctors() {
        return requestSpecification()
                .get(DoctorApiController.BASE_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorDTOs.class);
    }

    default DoctorDTO getDoctor(final String email) {
        return requestSpecification()
                .get(DoctorApiController.BASE_PATH + "/" + email)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorDTO.class);
    }

    default ExtractableResponse<Response> saveDoctor(final DoctorDTO employeeDTO) {
        return requestSpecification()
                .body(employeeDTO)
                .post(DoctorApiController.BASE_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }
}
