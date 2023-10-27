package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.domain.MeetingRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class MeetingRequestsDTOs {
    private List<MeetingRequest> meetingRequestList;
}
