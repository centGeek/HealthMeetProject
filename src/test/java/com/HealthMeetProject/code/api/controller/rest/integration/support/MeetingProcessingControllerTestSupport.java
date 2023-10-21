package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.MeetingProcessingApiController;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface MeetingProcessingControllerTestSupport {
    RequestSpecification requestSpecification();
    default ExtractableResponse<Response> getWaitingForConfirmationMeetingRequests(final Integer id) {
        return requestSpecification()
                .get(MeetingProcessingApiController.BASE_PATH+"/upcoming-visits/"+id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
    default ExtractableResponse<Response> findEndedVisitsByPatientEmail(final String email) {
        return requestSpecification()
                .get(MeetingProcessingApiController.BASE_PATH+"/ended-visits/"+email)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
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
