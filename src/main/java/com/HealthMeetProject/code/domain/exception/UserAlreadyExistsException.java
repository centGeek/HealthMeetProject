package com.HealthMeetProject.code.domain.exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String msg){
        super(msg);
    }

}
