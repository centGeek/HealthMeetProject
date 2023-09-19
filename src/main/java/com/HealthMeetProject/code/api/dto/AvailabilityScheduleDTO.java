package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AvailabilityScheduleDTO {
    private int availability_schedule_id;
    private LocalDateTime since;
    private LocalDateTime toWhen;
    private boolean availableDay;
    private boolean availableTerm;
    private DoctorDTO doctor;
}
