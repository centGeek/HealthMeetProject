package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceiptJpaRepository extends JpaRepository<ReceiptEntity, Integer> {
    @Query("""
            select receipt from ReceiptEntity  receipt where receipt.patient.email =:email
            """)
    List<ReceiptEntity> findPatientReceipts(@Param("email") String email);

    @Query("""           
            select receipt from ReceiptEntity  receipt where receipt.receiptNumber =:receiptNumber
            """)
    ReceiptEntity findByReceiptNumber(@Param("receiptNumber") String receiptNumber);

    @Query("""           
            select meetingRequestEntity from MeetingRequestEntity meetingRequestEntity where meetingRequestEntity.visitEnd =:visitEnd
            """)
    ReceiptEntity findByVisitEnd(LocalDateTime visitEnd);
}
