package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.business.DoctorService;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meeting-processing")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingProcessingApiController {

    private final DoctorDAO doctorDAO;
    private final MeetingRequestService meetingRequestService;
    private final MeetingRequestDAO meetingRequestDAO;
    private final DoctorService doctorService;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<?> getMeetingRequests(
            @PathVariable Integer doctorId
    ) {
        Doctor doctor = doctorDAO.findById(doctorId)
                .orElseThrow(() -> new ProcessingException("There is no doctor with the following identifier"));

        String email = doctorService.authenticateDoctor();
        List<MeetingRequest> meetingRequests = meetingRequestService.availableServiceRequestsByDoctor(email);

        List<MeetingRequest> filteredMeetingRequests = meetingRequests.stream()
                .filter(request -> request.getDoctor().getDoctorId()==(doctor.getDoctorId())
                        && request.getCompletedDateTime() == null)
                .collect(Collectors.toList());

        List<String> formattedDates = new ArrayList<>();
        formatDate(filteredMeetingRequests, formattedDates);

        return ResponseEntity.ok(filteredMeetingRequests);
    }

    @PatchMapping()
    public ResponseEntity<?> confirmMeetingRequest(
            @PathVariable Integer meetingRequestId
    ) {
        MeetingRequestEntity meetingRequestEntity = meetingRequestService.executeActionForMeetingRequest(meetingRequestId);
        return ResponseEntity.ok(meetingRequestEntity);
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> findEndedVisitsByPatientEmail(
            @RequestParam String patientEmail
    ) {
        String doctorEmail = doctorService.authenticateDoctor();

        List<MeetingRequest> allEndedUpVisits = meetingRequestDAO
                .findAllEndedUpVisitsByDoctorAndPatient(doctorEmail, patientEmail)
                .stream()
                .sorted(Comparator.comparing(MeetingRequest::getVisitEnd).reversed())
                .collect(Collectors.toList());

        List<String> formattedDates = new ArrayList<>();
        formatDate(allEndedUpVisits, formattedDates);

        return ResponseEntity.ok(allEndedUpVisits);
    }

    private void formatDate(List<MeetingRequest> meetingRequests, List<String> formattedDates) {
        for (MeetingRequest meetingRequest : meetingRequests) {
            String formatted = meetingRequest.getVisitEnd().format(FORMATTER);
            formattedDates.add(formatted);
        }
    }
}
