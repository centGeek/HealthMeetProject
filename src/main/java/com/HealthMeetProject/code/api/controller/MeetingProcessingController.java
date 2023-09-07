package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MeetingProcessingController {
    private DoctorJpaRepository doctorJpaRepository;
    private MeetingRequestService meetingRequestService;
    private MeetingRequestJpaRepository meetingRequestJpaRepository;
    private MeetingRequestEntityMapper meetingRequestEntityMapper;
    private DoctorService doctorService;
    public static final String MEETING_REQUESTS = "/doctor/{doctorId}/meeting_requests";

    @GetMapping(MEETING_REQUESTS)
    public String meetingRequestProcessingPage(
            @PathVariable Integer doctorId,
            Model model
    ) {
        DoctorEntity byId = doctorJpaRepository.findById(doctorId)
                .orElseThrow(() -> new ProcessingException("There is no doctor with following identifier"));

        List<MeetingRequest> meetingRequests = meetingRequestService.availableServiceRequests();
        List<MeetingRequest> collect = meetingRequests.stream().filter(request -> request.getDoctor().getDoctorId() == byId.getDoctorId()
                && request.getCompletedDateTime()==null).collect(Collectors.toList());

        List<MeetingRequest> endedVisits = meetingRequestService.availableEndedVisits();

        model.addAttribute("meetingRequests", collect);
        model.addAttribute("endedVisits", endedVisits);
        return "meeting_processing_doctor";
    }

    @PatchMapping("doctor/execute-action/{meetingRequestId}")
    public String confirmMeetingRequest(
            @PathVariable("meetingRequestId") Integer meetingRequestId
    ) {
         meetingRequestService.executeActionForMeetingRequest(meetingRequestId);
        return "redirect:/doctor";
    }
}
