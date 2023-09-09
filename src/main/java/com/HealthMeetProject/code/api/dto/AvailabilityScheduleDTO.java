package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityScheduleDTO {
    private int availability_schedule_id;
    private OffsetDateTime since;
    private OffsetDateTime toWhen;
    private boolean availableDay;
    private boolean availableTerm;
    private Doctor doctor;
}
