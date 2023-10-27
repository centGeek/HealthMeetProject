package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.MeetingProcessingApiController;
import com.HealthMeetProject.code.api.dto.api.MeetingRequestsDTOs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface MeetingProcessingControllerTestSupport {
    RequestSpecification requestSpecification();
    default MeetingRequestsDTOs getWaitingForConfirmationMeetingRequests(final Integer doctorId) {
        return requestSpecification()
                .get(MeetingProcessingApiController.BASE_PATH+"/upcoming-visits/"+doctorId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(MeetingRequestsDTOs.class);
    }
    default MeetingRequestsDTOs findEndedVisitsByPatientEmail(final String patientEmail, String doctorEmail) {
        return requestSpecification()
                .param("doctorEmail", doctorEmail)
                .get(MeetingProcessingApiController.BASE_PATH+"/ended-visits/"+patientEmail)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(MeetingRequestsDTOs.class);
    }

    default ExtractableResponse<Response> confirmMeetingRequest(Integer meetingRequestId) {
        return requestSpecification()
                .patch(MeetingProcessingApiController.BASE_PATH+"/"+meetingRequestId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
}
