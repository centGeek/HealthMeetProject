package com.HealthMeetProject.code.api.controller.rest;

import com.HealthMeetProject.code.domain.exception.AccessDeniedException;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionApiHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        String message = String.format("Other exception occurred: [%s]", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(NotFoundException ex) {
        String message = String.format("Could not find a resource: [%s]", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProcessingException.class)
    public ResponseEntity<Map<String, String>> handleProcessingException(ProcessingException ex) {
        String message = String.format("Processing exception occurred: [%s]", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException ex) {
        String field = Optional.ofNullable(ex.getFieldError()).map(FieldError::getField).orElse(null);
        String rejectedValue = (String) Optional.ofNullable(ex.getFieldError()).map(FieldError::getRejectedValue).orElse(null);
        String message = String.format("Bad request for field: [%s], wrong value: [%s]", field, rejectedValue);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
