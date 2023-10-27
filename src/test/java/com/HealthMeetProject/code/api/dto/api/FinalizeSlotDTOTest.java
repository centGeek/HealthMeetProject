package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.api.FinalizeSlotDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinalizeSlotDTOTest {

    @Test
    public void testFinalizeSlotDTO() {

        //given
        FinalizeSlotDTO finalizeSlotDTO1 = new FinalizeSlotDTO(1, 2, "doctor@example.com");
        //when, then
        assertEquals(1, finalizeSlotDTO1.getAvailabilityScheduleId());
        assertEquals(2, finalizeSlotDTO1.getSelectedSlotId());
        assertEquals("doctor@example.com", finalizeSlotDTO1.getDoctorEmail());

        //given
        FinalizeSlotDTO finalizeSlotDTO2 = FinalizeSlotDTO.builder()
                .availabilityScheduleId(3)
                .selectedSlotId(4)
                .doctorEmail("anotherdoctor@example.com")
                .build();
        //whem,then
        assertEquals(3, finalizeSlotDTO2.getAvailabilityScheduleId());
        assertEquals(4, finalizeSlotDTO2.getSelectedSlotId());
        assertEquals("anotherdoctor@example.com", finalizeSlotDTO2.getDoctorEmail());

        //given
        FinalizeSlotDTO finalizeSlotDTO3 = new FinalizeSlotDTO();
        //when, then
        assertNull(finalizeSlotDTO3.getAvailabilityScheduleId());
        assertNull(finalizeSlotDTO3.getSelectedSlotId());
        assertNull(finalizeSlotDTO3.getDoctorEmail());

        //given
        FinalizeSlotDTO finalizeSlotDTO4 = new FinalizeSlotDTO(5, 6, "yetanotherdoctor@example.com");
        //when,then
        assertEquals(5, finalizeSlotDTO4.getAvailabilityScheduleId());
        assertEquals(6, finalizeSlotDTO4.getSelectedSlotId());
        assertEquals("yetanotherdoctor@example.com", finalizeSlotDTO4.getDoctorEmail());
    }
}
