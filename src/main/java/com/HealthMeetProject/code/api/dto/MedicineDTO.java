package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Receipt;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineDTO {
    private String name;
    private int quantity;
    private BigDecimal approxPrice;
}
