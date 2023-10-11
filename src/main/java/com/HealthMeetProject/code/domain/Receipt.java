package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString(exclude = "medicine")
@AllArgsConstructor
@Builder
public class Receipt {
    private int receiptId;
    private String receiptNumber;
    private LocalDateTime dateTime;
    private Patient patient;
    private Doctor doctor;
    private Set<Medicine> medicine;

}
