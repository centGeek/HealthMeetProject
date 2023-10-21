package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static com.HealthMeetProject.code.util.DoctorExampleFixtures.doctorEntityExample1;
import static com.HealthMeetProject.code.util.PatientExampleFixtures.patientEntityExample1;
import static com.HealthMeetProject.code.util.PatientExampleFixtures.patientEntityExample2;
import static com.HealthMeetProject.code.util.ReceiptExampleFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiptJpaRepositoryTest {
    private final ReceiptJpaRepository receiptJpaRepository;
    private final PatientJpaRepository patientJpaRepository;
    private final DoctorJpaRepository doctorJpaRepository;
    @BeforeEach
    public void given(){
        //given
        Set<PatientEntity> patientEntities = Set.of(patientEntityExample1(), patientEntityExample2());
        patientJpaRepository.saveAllAndFlush(patientEntities);
        doctorJpaRepository.saveAndFlush(doctorEntityExample1());

        PatientEntity patient1 = patientJpaRepository.findByEmail(patientEntityExample1().getEmail()).get();
        PatientEntity patient2 = patientJpaRepository.findByEmail(patientEntityExample2().getEmail()).get();
        DoctorEntity doctor1 = doctorJpaRepository.findByEmail(doctorEntityExample1().getEmail()).get();

        ReceiptEntity receiptEntity1 = receiptEntityExampleData1().withPatient(patient1).withDoctor(doctor1);
        ReceiptEntity receiptEntity2 = receiptEntityExampleData2().withPatient(patient1).withDoctor(doctor1);
        ReceiptEntity receiptEntity3 = receiptEntityExampleData3().withPatient(patient2).withDoctor(doctor1);

        Set<ReceiptEntity> setOf = Set.of(receiptEntity1, receiptEntity2, receiptEntity3);
        receiptJpaRepository.saveAllAndFlush(setOf);
    }
    @Test
    void thatReceiptsAreFoundByClient(){
        //when
        List<ReceiptEntity> patientReceipts = receiptJpaRepository.findPatientReceipts(patientEntityExample1().getEmail());
        //then
        assertThat(patientReceipts.size()).isEqualTo(2);
    }
}