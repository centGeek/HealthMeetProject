package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Doctor;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityScheduleDTO {
    private int availability_schedule_id;
    private LocalDateTime since;
    private LocalDateTime toWhen;
    private boolean availableDay;
    private boolean availableTerm;
    private DoctorDTO doctor;
}
