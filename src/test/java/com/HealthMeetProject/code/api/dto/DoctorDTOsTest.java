package com.HealthMeetProject.code.api.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorDTOsTest {

    @Test
    public void testConstructorAndGetters() {
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        DoctorDTO doctor1 = new DoctorDTO();
        DoctorDTO doctor2 = new DoctorDTO();
        doctor1.setName("Dr. John Doe");
        doctor2.setName("Dr. Jane Smith");
        doctorDTOList.add(doctor1);

        DoctorDTOs doctorDTOs = DoctorDTOs.of(doctorDTOList);

        List<DoctorDTO> retrievedDoctorDTOs = doctorDTOs.getDoctorDTOList();
        assertEquals(doctorDTOList, retrievedDoctorDTOs);

        doctor1.setName("Dr. Alice Johnson");
        doctorDTOList.add(doctor1);
        doctorDTOs.setDoctorDTOList(doctorDTOList);

        List<DoctorDTO> retrievedDoctorDTOs2 = doctorDTOs.getDoctorDTOList();
        assertEquals(doctorDTOList, retrievedDoctorDTOs2);
    }
}

