package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ReceiptRepository implements ReceiptDAO {
    private final ReceiptJpaRepository receiptJpaRepository;
    private final ReceiptEntityMapper receiptEntityMapper;
    public List<Receipt> findPatientReceipts(String email){
        return receiptJpaRepository.findPatientReceipts(email).stream().map(receiptEntityMapper::mapFromEntity).toList();
    }
    public void save(Receipt receipt){
        ReceiptEntity receiptEntity = receiptEntityMapper.mapToEntity(receipt);
        receiptJpaRepository.save(receiptEntity);
    }

}
