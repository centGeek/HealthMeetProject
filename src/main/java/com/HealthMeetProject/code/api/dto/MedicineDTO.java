package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Receipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineDTO {
    private String name;
    private int quantity;
    private BigDecimal approxPrice;
}
