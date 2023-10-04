package com.HealthMeetProject.code.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class IllnessHistoryDTOs {
    private List<String> illnessList;
}
