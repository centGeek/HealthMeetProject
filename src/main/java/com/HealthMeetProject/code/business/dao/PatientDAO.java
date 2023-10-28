package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;

public interface PatientDAO {

    void issueInvoice(Patient patient);

    void saveMeetingRequest(MeetingRequest meetingRequest, Patient patient);

    Patient savePatient(Patient patient);

    Patient findById(Integer patientId);

    void register(PatientDTO patientDTO);

    Patient findByEmail(String email);

}