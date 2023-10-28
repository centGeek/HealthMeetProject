package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class DoctorDTOs {
    private List<DoctorDTO> doctorDTOList;
}
