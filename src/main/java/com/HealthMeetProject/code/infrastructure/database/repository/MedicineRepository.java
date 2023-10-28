package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MedicineJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MedicineRepository implements MedicineDAO {
    private final MedicineJpaRepository medicineJpaRepository;

    @Override
    public List<MedicineEntity> findByReceipt(Integer receiptId) {
        List<MedicineEntity> byReceipt = medicineJpaRepository.findByReceipt(receiptId);
        for (MedicineEntity medicineEntity : byReceipt) {
            medicineEntity.setReceipt(ReceiptEntity.builder().receiptId(receiptId).build());
        }
        return byReceipt;
    }

    @Override
    public List<MedicineEntity> findByPatientEmail(String email) {
        return medicineJpaRepository.findByPatientEmail(email);
    }
}
