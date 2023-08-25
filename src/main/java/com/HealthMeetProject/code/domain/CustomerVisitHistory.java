package com.HealthMeetProject.code.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerVisitHistory {
    String customerPesel;
    List<MeetingRequests> meetingRequests;

    @Value
    @Builder
    public static class MeetingRequests {
        Integer meetingId;
        String meetingRequestNumber;
        LocalDateTime receivedDateTime;
        LocalDateTime completedDateTime;
        String description;
        Doctor doctor;
    }

}
