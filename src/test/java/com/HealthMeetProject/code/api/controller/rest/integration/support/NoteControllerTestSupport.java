package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.NoteApiController;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.IllnessHistoryDTOs;
import com.HealthMeetProject.code.domain.Note;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.HealthMeetProject.code.api.controller.rest.PatientApiController.BASE_PATH;

public interface NoteControllerTestSupport {
    RequestSpecification requestSpecification();

    default Note getNote(final Integer meetingId) {
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .get(NoteApiController.BASE_PATH+"/{meetingId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(Note.class);
    }

    default ExtractableResponse<Response> addNote(final Integer meetingId) {
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .post(NoteApiController.BASE_PATH+ "/meeting/{meetingId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
    default IllnessHistoryDTOs getIllnessHistory(final Integer meetingId) {
        return requestSpecification()
                .pathParam("meetingId", meetingId)
                .delete(NoteApiController.BASE_PATH+"/{patient/{meetingId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(IllnessHistoryDTOs.class);
    }
}
