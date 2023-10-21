package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.NoteApiController;
import com.HealthMeetProject.code.api.dto.IllnessHistoryDTOs;
import com.HealthMeetProject.code.domain.Note;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface NoteControllerTestSupport {
    RequestSpecification requestSpecification();

    default Note getNote(final Integer meetingId, String illness) {
        return requestSpecification()
                .param("illness", illness)
                .get(NoteApiController.BASE_PATH+"/"+meetingId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(Note.class);
    }

    default ExtractableResponse<Response> addNote(final Integer meetingId, final Note note) {
        return requestSpecification()
                .body(note)
                .post(NoteApiController.BASE_PATH+"/"+meetingId)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }
    default IllnessHistoryDTOs getIllnessHistory(String email) {
        return requestSpecification()
                .get(NoteApiController.BASE_PATH+"/patient/"+email)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(IllnessHistoryDTOs.class);
    }
}
