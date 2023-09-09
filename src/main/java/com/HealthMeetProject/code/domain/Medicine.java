package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {
    private Integer medicineId;
    private String name;
    private int quantity;
    private BigDecimal approxPrice;
    private Receipt receipt;
}
