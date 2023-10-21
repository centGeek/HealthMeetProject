package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientEntityMapperTest {
    private PatientEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PatientEntityMapper.class);
    }

    @Test
    public void shouldMapPatientEntityToPatient() {
        // given
        PatientEntity patientEntity = PatientExampleFixtures.patientEntityExample1();
        patientEntity.setUser(PatientDTOFixtures.userDataPatientEntity());
        //when
        Patient patient = mapper.mapFromEntity(patientEntity);

        // then
        assertions(patient,patientEntity);
    }

    @Test
    public void shouldMapPatientToPatientEntity() {
        // given
        Patient patient = PatientExampleFixtures.patientExample1();
        patient.setUser(PatientDTOFixtures.userDataPatient());
        //when
        PatientEntity patientEntity = mapper.mapToEntity(patient);
        //then
        assertEquals(patient.getPatientId(), patientEntity.getPatientId());
        assertions(patient, patientEntity);
    }

    private static void assertions(Patient patient, PatientEntity patientEntity) {
        assertEquals(patient.getEmail(), patientEntity.getEmail());
        assertEquals(patient.getPesel(), patientEntity.getPesel());
        assertEquals(patient.getPhone(), patientEntity.getPhone());
        assertEquals(patient.getSurname(), patientEntity.getSurname());
        assertEquals(patient.getAddress().getCity(), patientEntity.getAddress().getCity());
        assertEquals(patient.getUser().getUserName(), patientEntity.getUser().getUserName());
    }
}