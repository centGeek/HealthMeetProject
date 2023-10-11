package com.HealthMeetProject.code.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilitySchedule {
    private int availability_schedule_id;
    private LocalDateTime since;
    private LocalDateTime toWhen;
    private boolean availableDay;
    private boolean availableTerm;
    private Doctor doctor;

}
