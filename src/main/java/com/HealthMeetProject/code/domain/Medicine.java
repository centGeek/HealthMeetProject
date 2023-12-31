package com.HealthMeetProject.code.domain;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@Setter
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
