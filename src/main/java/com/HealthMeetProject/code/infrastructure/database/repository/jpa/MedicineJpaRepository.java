package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineJpaRepository extends JpaRepository<MedicineEntity, Integer> {
    @Query("""
            select med from MedicineEntity med where med.receipt.receiptId=:receiptId
            """)
    List<MedicineEntity> findByReceipt(@Param("receiptId") Integer receiptId);

    @Query("""
            select med from MedicineEntity med where med.receipt.patient.email=:email
            """)
    List<MedicineEntity> findByPatientEmail(@Param("email") String email);
}
