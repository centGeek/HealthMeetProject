package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.controller.MeetingProcessingController;
import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.ReceiptDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/receipt")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiptApiController {
    private final MeetingRequestDAO meetingRequestDAO;
    private final ReceiptService receiptService;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;

    @GetMapping("/{meetingId}")
    public ResponseEntity<?> getReceiptPage(
            @PathVariable Integer meetingId
    ) {
        MeetingRequest meetingRequest = meetingRequestDAO.findById(meetingId);
        Doctor doctor = meetingRequest.getDoctor();
        Patient patient = meetingRequest.getPatient();

        ReceiptDTO receiptDTO = ReceiptDTO.builder()
                .now(OffsetDateTime.now().format(MeetingProcessingController.FORMATTER))
                .patientDTO(patientMapper.mapToDTO(patient))
                .doctorDTO(doctorMapper.mapToDTO(doctor))
                .meetingId(meetingId)
                .build();
        return ResponseEntity.ok(receiptDTO);
    }

    @PostMapping("/add/medicine/")
    public ResponseEntity<?> addMedicine(
            @RequestParam("medicine_name") String medicineName,
            @RequestParam("quantity") int quantity,
            @RequestParam("approx_price") BigDecimal approxPrice,
            @ModelAttribute("medicineList") List<MedicineDTO> medicineList
    ) {
        MedicineDTO build = MedicineDTO.builder()
                .name(medicineName)
                .quantity(quantity)
                .approxPrice(approxPrice)
                .build();
        medicineList.add(build);

        return ResponseEntity.ok(build);
    }

    @PostMapping("/issue/{meetingId}")
    public ResponseEntity<?> issueReceipt(
            @PathVariable Integer meetingId,
            @ModelAttribute("medicineList") List<MedicineDTO> medicineList
    ) {
        if (medicineList.isEmpty()) {
            throw new ProcessingException("Cannot issue receipt because you did not add any medicine");
        }
        MeetingRequest meetingRequest = meetingRequestDAO.findById(meetingId);
        Patient patient = meetingRequest.getPatient();
        receiptService.issueReceipt(medicineList, patient);
        return ResponseEntity.ok("Receipt issued successfully");
    }
}
