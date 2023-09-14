package com.HealthMeetProject.code.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilitySchedule {
    private int availability_schedule_id;
    private OffsetDateTime since;
    private OffsetDateTime toWhen;
    private boolean availableDay;
    private boolean availableTerm;
    private Doctor doctor;

}
