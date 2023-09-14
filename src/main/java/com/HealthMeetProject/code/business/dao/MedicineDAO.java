package com.HealthMeetProject.code.business.dao;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;

import java.util.List;

public interface MedicineDAO {
    List<MedicineEntity> findByReceipt(Integer receiptId);
    List<MedicineEntity> findByPatientEmail(String email);

}
