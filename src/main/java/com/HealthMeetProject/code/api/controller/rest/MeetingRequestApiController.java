package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTOs;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-requests")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestApiController {

    private final AvailabilityScheduleService availabilityScheduleService;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    private final MeetingRequestService meetingRequestService;
    private final PatientService patientService;
    private final PatientDAO patientDAO;
    private final AvailabilityScheduleDAO availabilityScheduleDAO;

    @GetMapping("/slot/{availabilityScheduleId}")
    public AvailabilityScheduleDTOs chooseAccurateTerm(
            @PathVariable Integer availabilityScheduleId
    ) {
        return AvailabilityScheduleDTOs.of(meetingRequestService.getParticularVisitTimeDTO(availabilityScheduleId));
    }


    @PostMapping("/finalize")
    public ResponseEntity<?> finalizeMeetingRequest(
            @RequestParam Integer availabilityScheduleId,
            @RequestParam Integer selectedSlotId
    ) {
        if(availabilityScheduleId==null || selectedSlotId==null){
            throw new ProcessingException("some unexpected error occurred");
        }
        AvailabilityScheduleDTO visitTermDTO = meetingRequestService.getAvailabilitySchedule(availabilityScheduleId, selectedSlotId);
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.findById(availabilityScheduleId);
        availabilitySchedule.setAvailableTerm(false);
        availabilityScheduleService.save(availabilitySchedule);

        return ResponseEntity.ok(visitTermDTO);
    }

    @PostMapping("/{availabilityScheduleId}")
    public ResponseEntity<?> addMeetingRequest(
            @PathVariable Integer availabilityScheduleId,
            @RequestParam String description
    ) {
        String email = patientService.authenticate();
        Patient patient = patientDAO.findByEmail(email);

        AvailabilityScheduleDTO visitTerm = availabilityScheduleMapper.mapToDTO(availabilityScheduleDAO.findById(availabilityScheduleId));
        DoctorDTO doctor = visitTerm.getDoctor();

        meetingRequestService.makeMeetingRequest(patient, doctor, description, visitTerm);

        return ResponseEntity.ok("Meeting request added successfully");
    }
}
