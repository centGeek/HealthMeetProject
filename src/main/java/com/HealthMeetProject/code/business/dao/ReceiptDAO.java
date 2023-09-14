package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Receipt;

import java.util.List;

public interface ReceiptDAO {
    List<Receipt> findPatientReceipts(String email);
}
