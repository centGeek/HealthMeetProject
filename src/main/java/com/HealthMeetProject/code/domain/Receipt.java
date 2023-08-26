package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private int meetingId;
    private String receiptNumber;
    private OffsetDateTime dateTime;
    private Patient patient;
    private Doctor doctor;
    private Set<Medicine> medicine;

}
