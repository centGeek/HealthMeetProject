package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class NoteController {
    public static final String NOTE_PAGE = "/doctor/issue/note/{meetingId}";
    public static final String NOTE_PAGE_ADD = "/doctor/issue/note/add/{meetingId}";

    private final MeetingRequestService meetingRequestService;
    private final MeetingRequestJpaRepository meetingRequestJpaRepository;
    private final MeetingRequestEntityMapper meetingRequestEntityMapper;
    private final DoctorService doctorService;
    @GetMapping(NOTE_PAGE)
    public String getNotePage(
            @PathVariable Integer meetingId,
            Model model
    ){
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingId)
                .orElseThrow(() -> new ProcessingException("Can not find meeting request"));
        DoctorEntity doctor = meetingRequestEntity.getDoctor();
        PatientEntity patient = meetingRequestEntity.getPatient();
        model.addAttribute("patient", patient);
        model.addAttribute("meetingId", meetingId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("now", LocalDateTime.now()); //do modyfikacji

        return "note";
    }
    @PostMapping(NOTE_PAGE_ADD)
    public String addNote(
            @PathVariable Integer meetingId,
            @RequestParam("illness") String illness,
            @RequestParam("description") String description
    ){
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingId)
                .orElseThrow(() -> new ProcessingException("Can not find meeting request"));
        DoctorEntity doctor = meetingRequestEntity.getDoctor();
        PatientEntity patient = meetingRequestEntity.getPatient();
        doctorService.writeNote(doctor, illness, description, patient);

        return "redirect:"+NOTE_PAGE;

    }
}
