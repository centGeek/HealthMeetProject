package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class PatientService {
    private PatientDAO patientDAO;

    void issueInvoice(Patient patient){
     patientDAO.issueInvoice(patient);
    }
    void saveMeetingRequest(MeetingRequest meetingRequest, Patient patient){
        patientDAO.saveMeetingRequest(meetingRequest, patient);
    }

    Patient savePatient(Patient patient){
        return patientDAO.savePatient(patient);
    }

    public void register(PatientDTO patientDTO) throws UserAlreadyExistsException {
        patientDAO.register(patientDTO);
    }
    public String authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                email = userDetails.getUsername();
            }
        }
        Objects.requireNonNull(email);
        return email;
    }
}
