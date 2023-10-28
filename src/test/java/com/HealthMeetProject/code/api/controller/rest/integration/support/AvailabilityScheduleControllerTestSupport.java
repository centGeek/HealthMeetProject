package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.AvailabilityScheduleApiController;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.api.AvailabilityScheduleDTOs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface AvailabilityScheduleControllerTestSupport {
    RequestSpecification requestSpecification();

    default AvailabilityScheduleDTOs getAllAvailableWorkingDays() {
        return requestSpecification()
                .get(AvailabilityScheduleApiController.BASE_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AvailabilityScheduleDTOs.class);
    }

    default AvailabilityScheduleDTOs getAllDoctorAvailableTerms(Integer doctorId) {
        return requestSpecification()
                .get(AvailabilityScheduleApiController.BASE_PATH + "/" + doctorId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AvailabilityScheduleDTOs.class);
    }

    default ExtractableResponse<Response> addTerm(final AvailabilityScheduleDTO availabilityScheduleDTO
    ) {

        return requestSpecification()
                .body(availabilityScheduleDTO)
                .post(AvailabilityScheduleApiController.BASE_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> deleteTerm(final Integer availabilityScheduleId) {
        return requestSpecification()
                .delete(AvailabilityScheduleApiController.BASE_PATH + "/" + availabilityScheduleId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
}
