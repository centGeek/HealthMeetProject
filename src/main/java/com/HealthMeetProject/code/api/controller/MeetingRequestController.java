package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Patient;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MeetingRequestController {
    public static final String MAKE_APPOINTMENT = "/patient/terms/appointment/{availability_schedule_id}";
    public static final String MAKE_APPOINTMENT_FINALIZE = "/patient/terms/appointment/{availability_schedule_id}/finalize/{selectedSlotId}";
    private final AvailabilityScheduleService availabilityScheduleService;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    private final MeetingRequestService meetingRequestService;
    private final PatientService patientService;
    private final PatientDAO patientDAO;
    private final AvailabilityScheduleDAO availabilityScheduleDAO;



    @GetMapping(MAKE_APPOINTMENT)
    public String chooseAccurateTerm(
            @PathVariable Integer availability_schedule_id,
            Model model
    ) {
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.findById(availability_schedule_id);
        AvailabilityScheduleDTO availabilityScheduleDTO = availabilityScheduleMapper.mapToDTO(availabilitySchedule);
        Doctor doctor = availabilitySchedule.getDoctor();
        List<AvailabilitySchedule> particularVisitTime = meetingRequestService.generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), doctor);
        List<AvailabilityScheduleDTO> particularVisitTimeDTO = particularVisitTime.stream().map(availabilityScheduleMapper::mapToDTO).toList();
        if (particularVisitTime.isEmpty()) {
            availabilitySchedule.setAvailableDay(false);
            availabilityScheduleService.save(availabilitySchedule);
        }
        List<String> since = new ArrayList<>();
        List<String> toWhen = new ArrayList<>();

        for (AvailabilityScheduleDTO givenTime : particularVisitTimeDTO) {
            since.add(givenTime.getSince().format(MeetingProcessingController.FORMATTER));
            toWhen.add(givenTime.getToWhen().format(MeetingProcessingController.FORMATTER));
        }
        model.addAttribute("givenAvailabilitySchedule", availabilityScheduleDTO);
        model.addAttribute("particularVisitTime", particularVisitTimeDTO);
        model.addAttribute("since", since);
        model.addAttribute("toWhen", toWhen);

        return "make_appointment";
    }


    @GetMapping(MAKE_APPOINTMENT_FINALIZE)
    public String finalizeMeetingRequest(
            @PathVariable Integer availability_schedule_id,
            @PathVariable Integer selectedSlotId,
            HttpSession session,
            Model model
    ) {
        AvailabilityScheduleDTO visitTermDTO = meetingRequestService.getAvailabilitySchedule(availability_schedule_id, selectedSlotId);
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.findById(availability_schedule_id);
        availabilitySchedule.setAvailableTerm(false);
        availabilityScheduleService.save(availabilitySchedule);

        model.addAttribute("visitTerm", visitTermDTO);
        session.setAttribute("visitTerm", visitTermDTO);
        return "finalize_meeting_request";
    }

    @PostMapping("/patient/terms/add/meeting_request")
    public String addMeetingRequest(
            @RequestParam("description") String description,
            HttpSession session,
            Model model
    ) {
        String email = patientService.authenticate();
        Patient patient = patientDAO.findByEmail(email);

        AvailabilityScheduleDTO visitTerm = (AvailabilityScheduleDTO) session.getAttribute("visitTerm");

        DoctorDTO doctor = visitTerm.getDoctor();

        meetingRequestService.makeMeetingRequest(patient, doctor, description, visitTerm);
        model.addAttribute("visitTerm", visitTerm);
        return "meeting_request_finalized";
    }


}
