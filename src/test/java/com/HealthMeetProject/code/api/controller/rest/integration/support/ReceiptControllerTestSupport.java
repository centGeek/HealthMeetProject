package com.HealthMeetProject.code.api.controller.rest.integration.support;

import com.HealthMeetProject.code.api.controller.rest.ReceiptApiController;
import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.ReceiptDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ReceiptControllerTestSupport {
    RequestSpecification requestSpecification();

    default ReceiptDTO getReceiptPage(String patientEmail) {
        return requestSpecification()
                .get(ReceiptApiController.BASE_PATH + "/" + patientEmail)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ReceiptDTO.class);
    }


    default ExtractableResponse<Response> issueReceipt(String patientEmail, String doctorEmail, List<MedicineDTO> medicineList) {
        return requestSpecification()
                .param("medicineList", medicineList)
                .param("patientEmail", patientEmail)
                .param("doctorEmail", doctorEmail)
                .post(ReceiptApiController.BASE_PATH + "/issue")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }


}
