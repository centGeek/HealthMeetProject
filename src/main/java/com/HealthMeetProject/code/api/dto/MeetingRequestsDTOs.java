package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.MeetingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class MeetingRequestsDTOs {
    private List<MeetingRequest> meetingRequestList;
}
