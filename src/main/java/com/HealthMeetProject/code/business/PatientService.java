package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.PatientHistoryDTO;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class PatientService {
    private PatientDAO patientDAO;
    void saveMeetingRequest(MeetingRequest meetingRequest, Patient patient){
        patientDAO.saveMeetingRequest(meetingRequest, patient);
    }

    @Transactional
    public PatientDTO register(PatientDTO patientDTO) throws UserAlreadyExistsException {
        patientDAO.register(patientDTO);
        return patientDTO;
    }
    public String authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                username = userDetails.getUsername();
            }
        }
        Objects.requireNonNull(username);
        return username;
    }

    public void conditionsToUpdate(PatientDTO updatedPatientDTO, Patient existingPatient) {
        if (updatedPatientDTO.getName() != null) {
            existingPatient.setName(updatedPatientDTO.getName());
        }
        if (updatedPatientDTO.getSurname() != null) {
            existingPatient.setSurname(updatedPatientDTO.getSurname());
        }
        if (updatedPatientDTO.getEmail() != null) {
            existingPatient.setEmail(updatedPatientDTO.getEmail());
            existingPatient.getUser().setEmail(updatedPatientDTO.getEmail());
        }
        if (updatedPatientDTO.getPhone() != null) {
            existingPatient.setPhone(updatedPatientDTO.getPhone());
        }
        if (updatedPatientDTO.getAddress() != null) {
            existingPatient.setAddress(updatedPatientDTO.getAddress());
        }
    }


}
