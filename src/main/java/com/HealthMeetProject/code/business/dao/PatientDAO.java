package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDAO {

    void issueInvoice(Patient patient);
    void saveMeetingRequest(Patient patient);

    Patient savePatient(Patient patient);


    void register(PatientDTO patientDTO);
}