package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class ReceiptController {
    public static final String RECEIPT_PAGE = "/doctor/issue/receipt/{meetingId}";
    public static final String RECEIPT_PAGE_ADD = "/doctor/issue/receipt/add/{meetingId}";
    public static final String RECEIPT_PAGE_ADD_MEDICINE = "/doctor/issue/receipt/add/medicine/{meetingId}";

    private final MeetingRequestJpaRepository meetingRequestJpaRepository;
    private final ReceiptService receiptService;

    @GetMapping(RECEIPT_PAGE)
    public String getReceiptPage(
            @PathVariable Integer meetingId,
            Model model,
            HttpSession session
    ) {
        @SuppressWarnings("unchecked") List<Medicine> medicineList = (List<Medicine>) session.getAttribute("medicineList");
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingId)
                .orElseThrow(() -> new ProcessingException("Can not find meeting request"));
        DoctorEntity doctor = meetingRequestEntity.getDoctor();
        PatientEntity patient = meetingRequestEntity.getPatient();
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("meetingId", meetingId);
        model.addAttribute("medicineList", medicineList);

        return "receipt";
    }

    @PostMapping(RECEIPT_PAGE_ADD_MEDICINE)
    public String addMedicine(
            @SuppressWarnings("unused") @PathVariable Integer meetingId,
            @RequestParam("medicine_name") String medicineName,
            @RequestParam("quantity") int quantity,
            @RequestParam("approx_price") BigDecimal approxPrice,
            HttpSession session
            ) {
        @SuppressWarnings("unchecked") List<Medicine> medicineList = (List<Medicine>) session.getAttribute("medicineList");
        Medicine build = Medicine.builder()
                .name(medicineName)
                .quantity(quantity)
                .approxPrice(approxPrice)
                .build();
        if(Objects.isNull(medicineList)){
            medicineList = new ArrayList<>();
        }
        medicineList.add(build);
        session.setAttribute("medicineList", medicineList);

        return "redirect:/doctor/issue/receipt/{meetingId}";
    }

    @PostMapping(RECEIPT_PAGE_ADD)
    public String issueReceipt(
            @PathVariable Integer meetingId,
            HttpSession session
    ) {
        @SuppressWarnings("unchecked") List<Medicine> medicineList = (List<Medicine>) session.getAttribute("medicineList");
        if(medicineList.isEmpty()){
            throw new ProcessingException("Can not issue receipt, because you did not added any medicine");
        }
        MeetingRequestEntity meetingRequestEntity = meetingRequestJpaRepository.findById(meetingId)
                .orElseThrow(() -> new ProcessingException("Can not find meeting request"));
        PatientEntity patient = meetingRequestEntity.getPatient();
        receiptService.issueReceipt(medicineList,patient);
        return "redirect:/doctor";
    }
}
