package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PatientService {
    private PatientDAO patientDAO;

    void issueInvoice(Patient patient){
     patientDAO.issueInvoice(patient);
    }
    void saveMeetingRequest(Patient patient){
        patientDAO.saveMeetingRequest(patient);
    }

    Patient savePatient(Patient patient){
        return patientDAO.savePatient(patient);
    }

    public void register(PatientDTO patientDTO) throws UserAlreadyExistsException {
        patientDAO.register(patientDTO);
    }
}
