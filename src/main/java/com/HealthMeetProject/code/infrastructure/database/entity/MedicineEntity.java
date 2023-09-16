package com.HealthMeetProject.code.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "receipt")
@Entity
@Builder
@Table(name = "medicine")
public class MedicineEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "medicine_id")
    private Integer medicineId;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "approx_price")
    private BigDecimal approxPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    private ReceiptEntity receipt;

}
