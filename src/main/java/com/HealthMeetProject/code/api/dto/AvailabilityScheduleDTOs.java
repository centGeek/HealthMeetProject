package com.HealthMeetProject.code.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@Builder
public class AvailabilityScheduleDTOs {
    private List<AvailabilityScheduleDTO> availabilityScheduleDTOList;
}
