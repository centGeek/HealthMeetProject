package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.util.NoteExampleData;
import com.HealthMeetProject.code.util.PatientExampleData;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.Set;

import static com.HealthMeetProject.code.util.PatientExampleData.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientJpaRepositoryTest {
    private PatientJpaRepository patientJpaRepository;
    public void given(){
        Set<PatientEntity> patientEntities = Set.of(patientExample1(), patientExample2(), patientExample3());
        patientJpaRepository.saveAllAndFlush(patientEntities);

    }
    @Test
    void findPatientByEmail(){
        given();
        //when
        Optional<PatientEntity> byEmail = patientJpaRepository.findByEmail(patientExample1().getEmail());
        //then
        Assertions.assertEquals(byEmail.get().getEmail(), patientExample1().getEmail());


    }

}
