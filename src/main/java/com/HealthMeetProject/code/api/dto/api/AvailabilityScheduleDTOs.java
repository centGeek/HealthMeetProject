package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class AvailabilityScheduleDTOs {
    private List<AvailabilityScheduleDTO> availabilityScheduleDTOList;
}
