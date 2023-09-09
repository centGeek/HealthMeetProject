package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReceiptService {
    private DoctorService doctorService;
    private DoctorDAO doctorDAO;
    private MeetingRequestService meetingRequestService;
    private PatientEntityMapper patientEntityMapper;
    private MedicineEntityMapper medicineEntityMapper;

    @Transactional
    public void issueReceipt(List<Medicine> medicineList, PatientEntity patientEntity) {
        Set<Medicine> medicineSet = new HashSet<>(medicineList);
        String email = doctorService.authenticateDoctor();
        Doctor doctor = doctorService.findByEmail(email);
        Patient patient = patientEntityMapper.mapFromEntity(patientEntity);
        Receipt receipt = buildReceipt(patient, doctor);
        Set<MedicineEntity> toEntityMedicine= new HashSet<>();
        for (Medicine medicine : medicineSet) {
            toEntityMedicine.add(medicineEntityMapper.map(medicine));
        }
        doctorDAO.issueReceipt(receipt, toEntityMedicine);
    }

    private Receipt buildReceipt(Patient patient, Doctor doctor) {
        return Receipt.builder()
                .receiptNumber(meetingRequestService.generateNumber(OffsetDateTime.now()))
                .dateTime(OffsetDateTime.now())
                .patient(patient)
                .doctor(doctor)
                .build();
    }
}

