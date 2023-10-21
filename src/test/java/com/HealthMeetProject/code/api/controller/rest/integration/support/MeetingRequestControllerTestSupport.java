package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.MeetingRequestApiController;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.MeetingRequestDTO;
import com.HealthMeetProject.code.api.dto.MeetingRequestsDTOs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface MeetingRequestControllerTestSupport {
    RequestSpecification requestSpecification();


    default AvailabilityScheduleDTOs getSlotsByMeetingRequest(final Integer availabilityScheduleId) {
        return requestSpecification()
                .get(MeetingRequestApiController.BASE_PATH + "/slot/" + availabilityScheduleId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AvailabilityScheduleDTOs.class);
    }

    default ExtractableResponse<Response> finalizeMeetingRequest(final Integer meetingId, final Integer selectedSlotId) {
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .pathParam("selectedSlotId", selectedSlotId)
                .get("/api/meeting-requests/finalize")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> addMeetingRequest(final MeetingRequestDTO meetingRequestDTO) {
        return requestSpecification()
                .body(meetingRequestDTO)
                .post(MeetingRequestApiController.BASE_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }

    default MeetingRequestsDTOs getMeetingRequests(String email) {
        return requestSpecification()
                .get(MeetingRequestApiController.BASE_PATH+"/"+email)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(MeetingRequestsDTOs.class);
    }
}
