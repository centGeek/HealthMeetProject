package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.MeetingRequestApiController;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface MeetingRequestControllerTestSupport {
    RequestSpecification requestSpecification();


    default AvailabilityScheduleDTOs getSlotsByMeetingRequest(final Integer meetingId){
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .get("/api/meeting-requests/slot/{availabilityScheduleId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AvailabilityScheduleDTOs.class);
    }
    default ExtractableResponse<Response> finalizeMeetingRequest(final Integer meetingId, final Integer selectedSlotId){
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .pathParam("selectedSlotId", selectedSlotId)
                .get("/api/meeting-requests/finalize")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }
    default ExtractableResponse<Response> addMeetingRequest(final Integer availabilityScheduleId, final String description, final String doctorEmail){
        return requestSpecification()
                .pathParam("availabilityScheduleId", availabilityScheduleId)
                .param("description", description)
                .param("doctorEmail", doctorEmail)
                .post("/api/meeting-requests/{availabilityScheduleId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
}
