package com.HealthMeetProject.code.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AllExceptionsTests {
    @Test
    public void testConstructorAndGetMessage() {
        String errorMessageNotFound = "Did not found given resource.";
        String errorMessageDeniedException = "there is no access to this resource.";
        String errorMessageAlreadyExistsException = "Given user exists.";
        String errorMessageProcessingException = "Some unexpected error occurred.";
        NotFoundException notFoundException = new NotFoundException(errorMessageNotFound);
        AccessDeniedException accessDeniedException = new AccessDeniedException(errorMessageDeniedException);
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException(errorMessageAlreadyExistsException);
        ProcessingException processingException = new ProcessingException(errorMessageProcessingException);

        assertEquals(errorMessageNotFound, notFoundException.getMessage());
        assertEquals(errorMessageDeniedException, accessDeniedException.getMessage());
        assertEquals(errorMessageAlreadyExistsException, userAlreadyExistsException.getMessage());
        assertEquals(errorMessageProcessingException, processingException.getMessage());
    }

    @Test
    public void testDefaultConstructor() {
        NotFoundException exception1 = new NotFoundException(null);
        AccessDeniedException exception2 = new AccessDeniedException(null);
        UserAlreadyExistsException exception3 = new UserAlreadyExistsException(null);
        ProcessingException exception4 = new ProcessingException(null);

        assertNull(exception1.getMessage());
        assertNull(exception2.getMessage());
        assertNull(exception3.getMessage());
        assertNull(exception4.getMessage());
    }
}
