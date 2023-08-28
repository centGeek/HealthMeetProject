package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientDAO {

    void issueInvoice(Patient patient);
    void saveMeetingRequest(Patient patient);

    Patient savePatient(Patient patient);

    Optional<Patient> findByEmail(String email);


}