package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityScheduleDTOsTest {
    @Test
    public void availabilityScheduleEquals(){
        AvailabilityScheduleDTOs availabilityScheduleDTOs = AvailabilityScheduleDTOs.of(List.of());
        availabilityScheduleDTOs.setAvailabilityScheduleDTOList(List.of(DoctorExampleFixtures.availabilityScheduleDTO1()));
        AvailabilityScheduleDTOs availabilityScheduleDTOs1 = AvailabilityScheduleDTOs.builder()
                .availabilityScheduleDTOList(List.of(DoctorExampleFixtures.availabilityScheduleDTO1())).build();
        Assertions.assertEquals(availabilityScheduleDTOs, availabilityScheduleDTOs1);
        Assertions.assertEquals(availabilityScheduleDTOs.hashCode(), availabilityScheduleDTOs1.hashCode());

    }

}