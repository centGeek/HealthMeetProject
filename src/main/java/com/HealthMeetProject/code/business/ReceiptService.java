package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.api.dto.mapper.MedicineMapper;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.*;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ReceiptService {
    private DoctorService doctorService;
    private DoctorDAO doctorDAO;
    private MeetingRequestService meetingRequestService;
    private MedicineEntityMapper medicineEntityMapper;
    private MedicineMapper medicineMapper;
    private MedicineDAO medicineDAO;


    @Transactional
    public void issueReceipt(List<MedicineDTO> medicineList, Patient patient) {
        Set<MedicineDTO> medicineSet = new HashSet<>(medicineList);
        String email = doctorService.authenticateDoctor();
        Doctor doctor = doctorService.findByEmail(email);
        Receipt receipt = buildReceipt(patient, doctor);
        Set<MedicineEntity> toEntityMedicine= new HashSet<>();
        for (MedicineDTO medicine : medicineSet) {
            Medicine medicineToEntity = medicineMapper.mapFromDTO(medicine);
            MedicineEntity medicineEntity = medicineEntityMapper.mapToEntity(medicineToEntity);
            toEntityMedicine.add(medicineEntity);
        }
        doctorDAO.issueReceipt(receipt, toEntityMedicine);
    }

    Receipt buildReceipt(Patient patient, Doctor doctor) {
        return Receipt.builder()
                .receiptNumber(meetingRequestService.generateNumber(OffsetDateTime.now()))
                .dateTime(OffsetDateTime.now())
                .patient(patient)
                .doctor(doctor)
                .build();
    }
    public List<MedicineEntity> getMedicinesFromLastVisit(List<Receipt> receipts) {
        Optional<Integer> maxReceiptId = receipts.stream()
                .map(Receipt::getReceiptId)
                .max(Integer::compareTo);
        List<MedicineEntity> medicinesFromLastVisit;
        if (maxReceiptId.isPresent()) {
            medicinesFromLastVisit = medicineDAO.findByReceipt(maxReceiptId.get());
        } else {
            medicinesFromLastVisit = new ArrayList<>();
        }
        return medicinesFromLastVisit;
    }


}

