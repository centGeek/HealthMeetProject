package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.api.MeetingRequestsDTOs;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MeetingProcessingApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingProcessingApiController {

    private final DoctorDAO doctorDAO;
    private final MeetingRequestService meetingRequestService;
    private final MeetingRequestDAO meetingRequestDAO;
    public static final String BASE_PATH = "/api/meeting-processing";

    @GetMapping("/upcoming-visits/{doctorId}")
    public MeetingRequestsDTOs getWaitingForConfirmationMeetingRequests(
            @PathVariable Integer doctorId
    ) {
        Doctor doctor = doctorDAO.findById(doctorId)
                .orElseThrow(() -> new ProcessingException("There is no doctor with the following identifier"));

        List<MeetingRequest> meetingRequests = meetingRequestService.restAvailableServiceRequestsByDoctor(doctor.getEmail());
        for (MeetingRequest meetingRequest : meetingRequests) {
            meetingRequest.setDoctor(doctor);
        }
        return MeetingRequestsDTOs.of(meetingRequests.stream()
                .filter(request -> request.getDoctor().getDoctorId() == (doctor.getDoctorId()))
                .filter(request -> request.getVisitStart()
                        .minusMinutes(5).isAfter(LocalDateTime.now()))
                .collect(Collectors.toList()));

    }

    @GetMapping("/ended-visits/{patientEmail}")
    public MeetingRequestsDTOs findEndedVisitsByPatientEmail(
            @PathVariable String patientEmail,
            @RequestParam String doctorEmail
    ) {

        return MeetingRequestsDTOs.of(meetingRequestDAO
                .restFindAllEndedUpVisitsByDoctorAndPatient(doctorEmail, patientEmail)
                .stream()
                .sorted(Comparator.comparing(MeetingRequest::getVisitEnd).reversed())
                .collect(Collectors.toList()));

    }

    @PatchMapping("/{meetingRequestId}")
    public ResponseEntity<?> confirmMeetingRequest(
            @PathVariable Integer meetingRequestId
    ) {
        MeetingRequestEntity meetingRequestEntity = meetingRequestService.executeActionForMeetingRequest(meetingRequestId);
        return ResponseEntity.ok(meetingRequestEntity);
    }

}
