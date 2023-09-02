package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityScheduleDTO {
    private OffsetDateTime since;
    private OffsetDateTime toWhen;
    private boolean availability;
    private Doctor doctor;
}
