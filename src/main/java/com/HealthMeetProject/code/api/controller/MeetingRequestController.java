package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MeetingRequestController {
    public static final String MAKE_APPOINTMENT = "/patient/terms/appointment/{availability_schedule_id}";
    public static final String MAKE_APPOINTMENT_FINALIZE = "/patient/terms/appointment/{availability_schedule_id}/finalize/{selectedSlotId}";
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private final DoctorEntityMapper doctorEntityMapper;
    private final MeetingRequestService meetingRequestService;
    private final PatientService patientService;
    private final PatientJpaRepository patientJpaRepository;
    private final PatientEntityMapper patientEntityMapper;

    @GetMapping(MAKE_APPOINTMENT)
    public String chooseAccurateTerm(
            @PathVariable Integer availability_schedule_id,
            Model model
    ) {
        AvailabilityScheduleEntity availabilitySchedule = availabilityScheduleJpaRepository.findById(availability_schedule_id)
                .orElseThrow(() -> new ProcessingException("some error occurred"));
        Doctor doctor = doctorEntityMapper.mapFromEntity(availabilitySchedule.getDoctor());
        List<AvailabilitySchedule> particularVisitTime = generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), doctor);
        model.addAttribute("givenAvailabilitySchedule", availabilitySchedule);
        model.addAttribute("particularVisitTime", particularVisitTime);
        return "make_appointment";
    }

    public List<AvailabilitySchedule> generateTimeSlots(OffsetDateTime since, OffsetDateTime toWhen, Doctor doctor) {
        List<AvailabilitySchedule> timeSlots = new ArrayList<>();
        since = since.withMinute((since.getMinute() / 5) * 5);
        OffsetDateTime currentSlot = since;

        while (currentSlot.isBefore(toWhen)) {
            AvailabilitySchedule slot = new AvailabilitySchedule(0, currentSlot, currentSlot.plusMinutes(15), true, doctor);
            timeSlots.add(slot);
            currentSlot = currentSlot.plusMinutes(15);
        }
        for (int i = 1; i < timeSlots.size(); i++) {
            timeSlots.get(i).setAvailability_schedule_id(i);
        }
        return timeSlots;
    }

    @GetMapping(MAKE_APPOINTMENT_FINALIZE)
    public String finalizeMeetingRequest(
            @PathVariable Integer availability_schedule_id,
            @PathVariable Integer selectedSlotId,
            String description,
            HttpSession session,
            Model model
    ) {
        AvailabilitySchedule visitTerm = getAvailabilitySchedule(availability_schedule_id, selectedSlotId);

        model.addAttribute("visitTerm", visitTerm);
        model.addAttribute("description", description);
        session.setAttribute("visitTerm", visitTerm);

        return "finalize_meeting_request";
    }

    @PostMapping("/patient/terms/add/meeting_request")
    public String addMeetingRequest(
            @RequestParam("description") String description,
            HttpSession session,
            Model model
    ) {
        String email = patientService.authenticate();
        PatientEntity byEmail = patientJpaRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("not found patient by email"));
        Patient patient = patientEntityMapper.mapFromEntity(byEmail);

        AvailabilitySchedule visitTerm = (AvailabilitySchedule) session.getAttribute("visitTerm");

        Doctor doctor = visitTerm.getDoctor();

        meetingRequestService.makeMeetingRequest(patient, doctor, description);
        model.addAttribute("visitTerm", visitTerm);
        return "meeting_request_finalized";
    }

    public AvailabilitySchedule getAvailabilitySchedule(Integer availability_schedule_id, Integer selectedSlotId) {
        AvailabilityScheduleEntity availabilitySchedule = availabilityScheduleJpaRepository.findById(availability_schedule_id)
                .orElseThrow(() -> new ProcessingException("some error occurred"));
        Doctor doctor = doctorEntityMapper.mapFromEntity(availabilitySchedule.getDoctor());
        List<AvailabilitySchedule> particularVisitTime = generateTimeSlots(availabilitySchedule.getSince(), availabilitySchedule.getToWhen(), doctor);
        return particularVisitTime.get(selectedSlotId);
    }
}
