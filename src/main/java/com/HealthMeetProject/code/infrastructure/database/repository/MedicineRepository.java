package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MedicineJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MedicineEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MedicineRepository implements MedicineDAO {
    private final MedicineJpaRepository medicineJpaRepository;
    private final MedicineEntityMapper medicineEntityMapper;
    @Override
    public List<MedicineEntity> findByReceipt(Integer receiptId) {
        return medicineJpaRepository.findByReceipt(receiptId);
    }

    @Override
    public List<MedicineEntity> findByPatientEmail(String email) {
        return medicineJpaRepository.findByPatientEmail(email);
    }
}
