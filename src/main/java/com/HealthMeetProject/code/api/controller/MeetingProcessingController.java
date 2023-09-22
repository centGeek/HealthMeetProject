package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MeetingProcessingController {
    private DoctorDAO doctorDAO;
    private MeetingRequestService meetingRequestService;
    private MeetingRequestDAO meetingRequestDAO;
    private DoctorService doctorService;
    public static final String MEETING_REQUESTS = "/doctor/{doctorId}/meeting_requests";
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping(MEETING_REQUESTS)
    public String meetingRequestProcessingPage(
            @PathVariable Integer doctorId,
            Model model
    ) {

        Doctor byId = doctorDAO.findById(doctorId)
                .orElseThrow(() -> new ProcessingException("There is no doctor with following identifier"));
        String email = doctorService.authenticateDoctor();
        List<MeetingRequest> meetingRequests = meetingRequestService.availableServiceRequestsByDoctor(email);
        List<MeetingRequest> collect = meetingRequests.stream().filter(request -> request.getDoctor().getDoctorId() == byId.getDoctorId()
                && request.getCompletedDateTime()==null).filter(request -> request.getVisitStart()
                .minusMinutes(5).isAfter(LocalDateTime.now())).collect(Collectors.toList());
        List<String> collectToString = new ArrayList<>();
        formatDate(collect, collectToString);

        List<MeetingRequest> endedVisits = meetingRequestService.availableEndedVisitsByDoctor(email)
                .stream().sorted(Comparator.comparing(MeetingRequest::getVisitEnd).reversed()).toList();

        List<MeetingRequest> allUpcomingVisits = meetingRequestDAO.findAllUpcomingCompletedVisitsByDoctor(email)
                .stream().sorted(Comparator.comparing(MeetingRequest::getVisitEnd)).toList();

        List<String> endedVisitsToString = new ArrayList<>();
        List<String> upcomingVisitsToString = new ArrayList<>();
        formatDate(endedVisits, endedVisitsToString);
        formatDate(allUpcomingVisits, upcomingVisitsToString);
        model.addAttribute("meetingRequests", collect);
        model.addAttribute("meetingRequestsDatesToString", collectToString);
        model.addAttribute("endedVisits", endedVisits);
        model.addAttribute("endedVisitsToString", endedVisitsToString);
        model.addAttribute("allUpcomingVisits", allUpcomingVisits);
        model.addAttribute("upcomingVisitsToString", upcomingVisitsToString);
        return "meeting_processing_doctor";
    }

    private static void formatDate(List<MeetingRequest> endedVisits, List<String> endedVisitsToString) {
        for (MeetingRequest endedVisit : endedVisits) {
            String formatted = endedVisit.getVisitStart().format(FORMATTER);
            endedVisitsToString.add(formatted);
        }
    }

    @PatchMapping("doctor/execute-action/{meetingRequestId}")
    public String confirmMeetingRequest(
            @PathVariable("meetingRequestId") Integer meetingRequestId
    ) {
         meetingRequestService.executeActionForMeetingRequest(meetingRequestId);
        return "redirect:/doctor";
    }
    @GetMapping("/doctor/search-by/email")
    public String findByPatientEmail(
            @RequestParam String patientEmail,
            Model model
    ){
        String doctorEmail = doctorService.authenticateDoctor();
        List<MeetingRequest> meetingRequests = meetingRequestService.availableServiceRequestsByDoctor(doctorEmail);
        List<MeetingRequest> allEndedUpVisits= meetingRequestDAO.findAllEndedUpVisitsByDoctorAndPatient(doctorEmail, patientEmail).stream()
                .sorted(Comparator.comparing(MeetingRequest::getVisitEnd).reversed()).toList();
        List<String> date = new ArrayList<>();
        formatDate(allEndedUpVisits, date);
        model.addAttribute("date", date);
        model.addAttribute("meetingRequests", meetingRequests);
        model.addAttribute("allEndedUpVisits", allEndedUpVisits);
        return "meeting_processing_doctor_searched";
    }
}
