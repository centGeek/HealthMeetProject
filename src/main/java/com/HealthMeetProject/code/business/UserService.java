package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;

public interface UserService {
    void register(final UserData user) throws UserAlreadyExistsException;
    boolean checkIfUserExists(final String email);
}
