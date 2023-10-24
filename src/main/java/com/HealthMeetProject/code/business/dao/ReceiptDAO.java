package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Receipt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReceiptDAO {
    List<Receipt> findPatientReceipts(String email);

    void save(Receipt receipt);

}