package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteJpaRepositoryTest {
    private final NoteJpaRepository noteJpaRepository;
    private final PatientJpaRepository patientJpaRepository;
    private final DoctorJpaRepository doctorJpaRepository;
    @BeforeEach
    public void given(){
        PatientEntity patientEntity = NoteExampleFixtures.patientEntityExample1();
        DoctorEntity doctor = DoctorExampleFixtures.doctorEntityExample1();
        doctorJpaRepository.saveAndFlush(doctor);
        patientJpaRepository.saveAndFlush(patientEntity);

        NoteEntity noteEntity = NoteExampleFixtures.noteEntityExample1();
        noteEntity.setPatient(patientEntity);
        noteEntity.setDoctor(doctor);
        noteJpaRepository.saveAndFlush(noteEntity);

    }
    @Test
    void thatNotesAreFoundByCustomer(){
        //when
        List<NoteEntity> byPatient = noteJpaRepository.findByPatientEmail(PatientExampleFixtures.patientEntityExample1().getEmail());
        //then
        Assertions.assertEquals(byPatient.size(),1);
    }

}


