package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.PatientVisitsHistory;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;

public interface PatientHistoryEntityMapper {

//    default PatientVisitsHistory mapFromEntity(
//            String email, PatientEntity patient,
//            DoctorEntity doctor, DoctorEntityMapper doctorEntityMapper,
//            PatientEntityMapper patientEntityMapper) {
//        return PatientVisitsHistory.builder()
//                .email(email)
//                .meetingRequests(patient.getMeetingRequests().stream()
//                        .map(request -> PatientVisitsHistory.MeetingRequests.builder()
//                                .meetingRequestNumber(request.getMeetingRequestNumber())
//                                .receivedDateTime(request.getReceivedDateTime())
//                                .completedDateTime(request.getCompletedDateTime())
//                                .description(request.getDescription())
//                                .patient(patientEntityMapper.mapFromEntity(patient))
//                                .doctor(doctorEntityMapper.mapFromEntity(doctor)).build()).toList()).build();
//
//    }
}
