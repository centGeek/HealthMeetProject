package com.HealthMeetProject.code.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeetingRequestDTOTest {

    @Test
    public void testClassMethods() {
        //given
        MeetingRequestDTO meetingRequestDTO = new MeetingRequestDTO();
        meetingRequestDTO.setAvailabilityScheduleId(2);
        meetingRequestDTO.setDescription("New Description");
        meetingRequestDTO.setPatientEmail("newpatient@example.com");
        //when, then
        assertEquals(2, meetingRequestDTO.getAvailabilityScheduleId());
        assertEquals("New Description", meetingRequestDTO.getDescription());
        assertEquals("newpatient@example.com", meetingRequestDTO.getPatientEmail());
        //given
        meetingRequestDTO = new MeetingRequestDTO(1, "Description", "patient@example.com");
        //when, then
        assertEquals(1, meetingRequestDTO.getAvailabilityScheduleId());
        assertEquals("Description", meetingRequestDTO.getDescription());
        assertEquals("patient@example.com", meetingRequestDTO.getPatientEmail());
        MeetingRequestDTO meetingRequestDTO1 = new MeetingRequestDTO();
        assertNotEquals(meetingRequestDTO, meetingRequestDTO1);
    }


    @Test
    public void testNonNullAnnotation() {
        try {
            new MeetingRequestDTO(null, "Description", "patient@example.com");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
    }
}
