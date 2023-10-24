package com.HealthMeetProject.code.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String msg){
        super(msg);
    }

}
