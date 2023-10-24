package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.*;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(MeetingRequestApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestApiController {
    public static final String BASE_PATH = "/api/meeting-requests";
    private final AvailabilityScheduleService availabilityScheduleService;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    private final MeetingRequestService meetingRequestService;
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
        if (availabilityScheduleId == null || selectedSlotId == null) {
            throw new ProcessingException("some unexpected error occurred");
        }
        AvailabilityScheduleDTO visitTermDTO = meetingRequestService.getAvailabilitySchedule(availabilityScheduleId, selectedSlotId);
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.findById(availabilityScheduleId);
        availabilitySchedule.setAvailableTerm(false);
        availabilityScheduleService.save(availabilitySchedule);

        return ResponseEntity.ok(visitTermDTO);
    }

    @PostMapping
    public ResponseEntity<MeetingRequestDTO> addMeetingRequest(
            @RequestBody @Valid MeetingRequestDTO meetingRequestDTO) {
        try {
            Patient patient = patientDAO.findByEmail(meetingRequestDTO.getPatientEmail());

            AvailabilityScheduleDTO visitTerm = availabilityScheduleMapper.mapToDTO(availabilityScheduleDAO.findById(meetingRequestDTO.getAvailabilityScheduleId()));
            DoctorDTO doctor = visitTerm.getDoctor();

            meetingRequestService.makeMeetingRequest(patient, doctor, meetingRequestDTO.getDescription(), visitTerm);
            return ResponseEntity
                    .created(URI.create(BASE_PATH))
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{email}")
    public MeetingRequestsDTOs getMeetingRequestsByDoctor(
            @PathVariable String email
    ){
        List<MeetingRequest> all = meetingRequestService.restFindByDoctorEmail(email);
        return MeetingRequestsDTOs.of(all);
    }
}

