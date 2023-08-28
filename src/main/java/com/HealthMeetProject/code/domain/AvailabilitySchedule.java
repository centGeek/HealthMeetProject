package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySchedule {
    private int availability_schedule_id;
    private OffsetDateTime since;
    private OffsetDateTime toWhen;
    private Doctor doctor;
    private Boolean availableTime;

}
