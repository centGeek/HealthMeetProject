package com.HealthMeetProject.code.api.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalizeSlotDTO {
    private Integer availabilityScheduleId;
    private Integer selectedSlotId;
    private String doctorEmail;
}
