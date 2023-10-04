package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.DoctorApiController;
import com.HealthMeetProject.code.api.controller.rest.PatientApiController;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface PatientControllerTestSupport {
    RequestSpecification requestSpecification();

    default DoctorDTO getDoctor(final String path) {
        return requestSpecification()
                .get(path)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorDTO.class);
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
    default PatientDTO findByEmail(final String email){
        return requestSpecification()
                .pathParam("email", email)
                .get(PatientApiController.BASE_PATH+"/by-email/{email}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(PatientDTO.class);
    }



    default ExtractableResponse<Response> deleteMeeting(final Integer meetingId) {
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .delete(PatientApiController.BASE_PATH+"/meeting/{meetingId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
}
