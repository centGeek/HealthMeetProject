package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.GlobalExceptionApiHandler;
import com.HealthMeetProject.code.domain.exception.AccessDeniedException;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionApiHandlerTest {

    private GlobalExceptionApiHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionApiHandler();
    }

    @Test
    void handleException_ShouldReturnInternalServerError() {
        Exception ex = new Exception("Test exception");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Other exception occurred: [Test exception]", Objects.requireNonNull(response.getBody()).get("errorMessage"));
    }

    @Test
    void handleAccessDeniedException_ShouldReturnForbidden() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAccessDeniedException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied", Objects.requireNonNull(response.getBody()).get("errorMessage"));
    }

    @Test
    void handleNoResourceFound_ShouldReturnNotFound() {
        NotFoundException ex = new NotFoundException("Resource not found");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleNoResourceFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Could not find a resource: [Resource not found]", Objects.requireNonNull(response.getBody()).get("errorMessage"));
    }

    @Test
    void handleProcessingException_ShouldReturnInternalServerError() {
        ProcessingException ex = new ProcessingException("Processing error");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleProcessingException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Processing exception occurred: [Processing error]", Objects.requireNonNull(response.getBody()).get("errorMessage"));
    }

@Test
    void handleIllegalArgumentException_ShouldReturnBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", Objects.requireNonNull(response.getBody()).get("errorMessage"));
    }
    @Test
    void handleBindException_ShouldReturnBadRequest() {
        Object targetObject = new Object();

        org.springframework.validation.FieldError fieldError = new org.springframework.validation.FieldError(
                "objectName", "fieldName", "rejectedValue", true, null, null, "defaultMessage");

        BindException ex = new BindException(targetObject, "fieldName");
        ex.addError(fieldError);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleBindException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request for field: [fieldName], wrong value: [rejectedValue]", Objects.requireNonNull(response.getBody()).get("errorMessage"));
    }
}
