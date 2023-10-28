package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientMapperTest {
    private PatientMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PatientMapper.class);
    }

    @Test
    public void shouldMapPatientEntityToPatient() {
        // given
        Patient patient = PatientExampleFixtures.patientExample1();
        patient.setUser(PatientDTOFixtures.userDataPatient());
        //when
        PatientDTO patientDTO = mapper.mapToDTO(patient);

        // then
        assertions(patientDTO, patient);
    }

    private static void assertions(PatientDTO patientDTO, Patient patient) {
        assertEquals(patientDTO.getEmail(), patient.getEmail());
        assertEquals(patientDTO.getPesel(), patient.getPesel());
        assertEquals(patientDTO.getPhone(), patient.getPhone());
        assertEquals(patientDTO.getSurname(), patient.getSurname());
        assertEquals(patientDTO.getAddress().getCity(), patient.getAddress().getCity());
        assertEquals(patientDTO.getUser().getUserName(), patient.getUser().getUserName());
    }
}