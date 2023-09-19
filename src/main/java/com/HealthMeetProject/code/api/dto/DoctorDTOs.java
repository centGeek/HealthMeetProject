package com.HealthMeetProject.code.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class DoctorDTOs {
    private List<DoctorDTO> doctorDTOList;
}
