package com.HealthMeetProject.code.api.dto.api;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class IllnessHistoryDTOs {
    private List<String> illnessList;
}
