package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;

public interface UserService {
    void register(final DoctorDTO doctorDTO) throws UserAlreadyExistsException;
    void register(final PatientDTO patientDTO) throws UserAlreadyExistsException;
    boolean checkIfUserExists(final String email);
}
