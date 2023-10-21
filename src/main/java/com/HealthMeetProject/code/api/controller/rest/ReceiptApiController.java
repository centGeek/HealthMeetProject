package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.Receipts;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ReceiptApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiptApiController {
    private final ReceiptService receiptService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ReceiptRepository receiptRepository;
    public static final String BASE_PATH = "/api/receipt";

    @GetMapping("/{patientEmail}")
    public Receipts getReceiptPage(@PathVariable String patientEmail
    ) {
        List<Receipt> patientReceipts = receiptRepository.findPatientReceipts(patientEmail);

        return Receipts.of(patientReceipts);
    }


    @PostMapping("/issue")
    public ResponseEntity<?> issueReceipt(
            @RequestParam String patientEmail,
            @RequestParam String doctorEmail,
            @RequestParam List<MedicineDTO> medicineList
    ) {
        if (medicineList.isEmpty()) {
            throw new ProcessingException("Cannot issue receipt because you did not add any medicine");
        }
        Patient patient = patientRepository.findByEmail(patientEmail);
        Doctor doctor = doctorRepository.findByEmail(doctorEmail).orElseThrow();
        receiptService.restIssueReceipt(medicineList, patient,doctor);
        return ResponseEntity
                .created(URI.create(BASE_PATH))
                .build();
    }
}
