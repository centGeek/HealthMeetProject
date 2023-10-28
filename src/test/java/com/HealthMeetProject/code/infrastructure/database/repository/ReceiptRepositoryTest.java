package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import com.HealthMeetProject.code.util.ReceiptExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptRepositoryTest {

    @Mock
    private ReceiptJpaRepository receiptJpaRepository;

    @Mock
    private ReceiptEntityMapper receiptEntityMapper;
    @InjectMocks
    private ReceiptRepository receiptRepository;


    @Test
    public void testFindPatientReceipts() {
        //given
        String email = "test@example.com";
        List<Receipt> expectedReceipts = List.of(ReceiptExampleFixtures.receiptExampleData1());
        //when
        when(receiptJpaRepository.findPatientReceipts(email)).thenReturn(List.of(ReceiptExampleFixtures.receiptEntityExampleData1()));
        when(receiptEntityMapper.mapFromEntity(ReceiptExampleFixtures.receiptEntityExampleData1())).thenReturn(ReceiptExampleFixtures.receiptExampleData1());

        List<Receipt> actualReceipts = receiptRepository.findPatientReceipts(email);

        //then
        assertEquals(expectedReceipts.get(0), actualReceipts.get(0));
        verify(receiptJpaRepository, times(1)).findPatientReceipts(email);
        assertEquals(expectedReceipts.size(), actualReceipts.size());
    }

    @Test
    public void testSaveReceipt() {
        ReceiptEntity receiptEntity = new ReceiptEntity();
        Mockito.when(receiptJpaRepository.save(Mockito.any(ReceiptEntity.class))).thenReturn(receiptEntity);
        receiptJpaRepository.save(receiptEntity);
        Mockito.verify(receiptJpaRepository, Mockito.times(1)).save(Mockito.any(ReceiptEntity.class));
    }

}
