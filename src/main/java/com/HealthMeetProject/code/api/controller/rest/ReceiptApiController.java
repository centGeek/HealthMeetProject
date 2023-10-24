package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.Receipts;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ReceiptApiController.BASE_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiptApiController {
    private final ReceiptService receiptService;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorRepository;
    private final ReceiptDAO receiptDAO;
    private final MedicineDAO medicineDAO;
    private final MedicineEntityMapper medicineEntityMapper;
    public static final String BASE_PATH = "/api/receipt";

    @GetMapping("/{patientEmail}")
    public Receipts getReceiptPage(@PathVariable String patientEmail
    ) {
        List<Receipt> patientReceipts = receiptDAO.findPatientReceipts(patientEmail);

        for (Receipt patientReceipt : patientReceipts) {
            Set<Medicine> byReceipt = medicineDAO.findByReceipt(patientReceipt.getReceiptId())
                    .stream().map(medicineEntityMapper::mapFromEntity).collect(Collectors.toSet());
            patientReceipt.setMedicine(byReceipt);
        }
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
        Patient patient = patientDAO.findByEmail(patientEmail);
        Doctor doctor = doctorRepository.findByEmail(doctorEmail).orElseThrow();
        receiptService.restIssueReceipt(medicineList, patient,doctor);
        return ResponseEntity
                .created(URI.create(BASE_PATH))
                .build();
    }
}
