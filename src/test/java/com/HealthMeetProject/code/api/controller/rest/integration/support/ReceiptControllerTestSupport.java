package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.ReceiptApiController;
import com.HealthMeetProject.code.api.dto.api.IssueReceiptDTO;
import com.HealthMeetProject.code.api.dto.api.Receipts;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface ReceiptControllerTestSupport {
    RequestSpecification requestSpecification();

    default Receipts getReceiptPage(String patientEmail) {
        return requestSpecification()
                .get(ReceiptApiController.BASE_PATH + "/" + patientEmail)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(Receipts.class);
    }


    default ExtractableResponse<Response> issueReceipt(IssueReceiptDTO issueReceiptDTO) {
        return requestSpecification()
                .body(issueReceiptDTO)
                .post(ReceiptApiController.BASE_PATH + "/issue")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }


}
