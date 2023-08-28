package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@With
public class PatientService {
    private PatientDAO patientDAO;

    void issueInvoice(Patient patient){
     patientDAO.issueInvoice(patient);
    }
    public void saveMeetingRequest(Patient patient){
       patientDAO.saveMeetingRequest(patient);
    }

    Patient savePatient(Patient patient){
        return patientDAO.savePatient(patient);
    }
    Patient findByEmail(String email){
        Optional<Patient> byEmail = patientDAO.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new RuntimeException("Can not find patient by email: [%s]".formatted(email));
        }
        return byEmail.get();
    }
}
