package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.PatientApiController;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface PatientControllerTestSupport {
    RequestSpecification requestSpecification();

    default ExtractableResponse<Response> updatePatient(Integer patientId, PatientDTO patientDTO) {
        return requestSpecification()
                .body(patientDTO)
                .patch(PatientApiController.BASE_PATH + "/" + patientId + "/edit")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> savePatient(final PatientDTO patientDTO) {
        return requestSpecification()
                .body(patientDTO)
                .post(PatientApiController.BASE_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }

    default PatientDTO findByEmail(final String email) {
        return requestSpecification()
                .pathParam("email", email)
                .get(PatientApiController.BASE_PATH + "/by-email/{email}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(PatientDTO.class);
    }


    default ExtractableResponse<Response> deleteMeeting(final Integer meetingId) {
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .delete(PatientApiController.BASE_PATH + "/meeting/{meetingId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
}
