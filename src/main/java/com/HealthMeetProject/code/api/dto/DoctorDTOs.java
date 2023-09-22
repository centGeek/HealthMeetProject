package com.HealthMeetProject.code.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class DoctorDTOs {
    private List<DoctorDTO> doctorDTOList;
}
