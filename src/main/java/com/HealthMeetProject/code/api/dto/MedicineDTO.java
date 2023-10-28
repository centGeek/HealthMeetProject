package com.HealthMeetProject.code.api.dto;

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
    private String patientEmail;
}
