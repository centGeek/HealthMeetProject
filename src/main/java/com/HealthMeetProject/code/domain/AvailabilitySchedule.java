package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySchedule {
    private int availability_schedule_id;
    private OffsetDateTime since;
    private OffsetDateTime toWhen;
    private Doctor doctor;

}
