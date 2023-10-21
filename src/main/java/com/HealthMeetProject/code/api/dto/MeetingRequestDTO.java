package com.HealthMeetProject.code.api.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRequestDTO {
    @NonNull
    private Integer availabilityScheduleId;
    private String description;
    private String patientEmail;
}
