package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.domain.Receipt;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptsTest {

    @Test
    public void testGetAndSetReceipts() {
        //given
        Receipt receipt1 = new Receipt();
        Receipt receipt2 = Receipt.builder().receiptId(2).build();
        receipt1.setReceiptId(1);
        //when
        Receipts receipts = Receipts.of(List.of(receipt1, receipt2));
        Receipts retrievedReceipts = Receipts.of(List.of());
        retrievedReceipts.setReceipts(List.of(receipt1, receipt2));
        //then
        Receipts receipts1 = new Receipts();
        Receipts receipts2 = Receipts.of(null);
        assertEquals(receipts1, receipts2);
        List<Receipt> retrievedReceipts1 = receipts.getReceipts();
        assertEquals(retrievedReceipts1, retrievedReceipts.getReceipts());
        assertEquals(retrievedReceipts1.get(0), retrievedReceipts.getReceipts().get(0));
        assertEquals(receipts, retrievedReceipts);
    }
}
