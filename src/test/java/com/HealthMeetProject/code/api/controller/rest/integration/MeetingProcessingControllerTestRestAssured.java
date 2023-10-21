package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingProcessingControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingRequestControllerTestSupport;
import com.HealthMeetProject.code.api.dto.MeetingRequestDTO;
import org.junit.jupiter.api.Test;

public class MeetingProcessingControllerTestRestAssured extends RestAssuredIntegrationTestBase
    implements MeetingProcessingControllerTestSupport, MeetingRequestControllerTestSupport {
    @Test
    public void thatMeetingRequestIsProcessedWell(){
//        MeetingRequestDTO.builder().availabilityScheduleId().patientEmail().description().build();
//        addMeetingRequest()
//        confirmMeetingRequest()
    }
}
