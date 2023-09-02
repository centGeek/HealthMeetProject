package com.HealthMeetProject.code.business.dao;

import com.HealthMeetProject.code.domain.MeetingRequest;

import java.util.List;

public interface PatientVisitHistoryDAO {
    List<MeetingRequest> showAllVisitHistory();
}
