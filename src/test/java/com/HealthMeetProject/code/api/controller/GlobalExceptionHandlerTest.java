package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.domain.exception.AccessDeniedException;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private Model model;

    @Test
    public void testHandleException() {
        Exception ex = new Exception("Test exception");
        ModelAndView modelAndView = exceptionHandler.handleException(ex);

        assertEquals("error", modelAndView.getViewName());
        assertEquals("Other exception occurred: [Test exception]", modelAndView.getModel().get("errorMessage"));
    }

    @Test
    public void testHandleAccessDeniedException() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");
        String viewName = exceptionHandler.handleAccessDeniedException(ex, model);

        assertEquals("error", viewName);
    }

    @Test
    public void testHandleNoResourceFound() {
        NotFoundException ex = new NotFoundException("Resource not found");
        ModelAndView modelAndView = exceptionHandler.handleNoResourceFound(ex);

        assertEquals("error", modelAndView.getViewName());
        assertEquals("Could not find a resource: [Resource not found]", modelAndView.getModel().get("errorMessage"));
    }

    @Test
    public void testHandleProcessingException() {
        ProcessingException ex = new ProcessingException("Processing error");
        ModelAndView modelAndView = exceptionHandler.handleException(ex);

        assertEquals("error", modelAndView.getViewName());
        assertEquals("Processing exception occurred: [Processing error]", modelAndView.getModel().get("errorMessage"));
    }


    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");
        String viewName = exceptionHandler.handleIllegalArgumentException(ex, model);

        assertEquals("error", viewName);
    }
}
