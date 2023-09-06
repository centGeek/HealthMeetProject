package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static com.HealthMeetProject.code.util.DoctorExampleFixtures.*;
import static com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures.*;
import static com.HealthMeetProject.code.util.PatientExampleFixtures.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestJpaRepositoryTest {

    private final MeetingRequestJpaRepository meetingRequestJpaRepository;
    private final DoctorJpaRepository doctorJpaRepository;
    private final PatientJpaRepository patientJpaRepository;
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @BeforeEach
    public void given() {
       Set<DoctorEntity> doctors = Set.of(doctorEntityExample1(), doctorEntityExample2(), doctorEntityExample3());
        Set<PatientEntity> patients = Set.of(patientExample1(),patientExample2(), patientExample3());
        doctorJpaRepository.saveAllAndFlush(doctors);
        patientJpaRepository.saveAllAndFlush(patients);


        PatientEntity patientEntity1 = patientJpaRepository.findByEmail(patientExample1().getEmail()).get();
        PatientEntity patientEntity2 = patientJpaRepository.findByEmail(patientExample2().getEmail()).get();
        PatientEntity patientEntity3 = patientJpaRepository.findByEmail(patientExample3().getEmail()).get();

        DoctorEntity doctorEntity1 = doctorJpaRepository.findByEmail(doctorEntityExample1().getEmail()).get();
        DoctorEntity doctorEntity2 = doctorJpaRepository.findByEmail(doctorEntityExample2().getEmail()).get();
        DoctorEntity doctorEntity3 = doctorJpaRepository.findByEmail(doctorEntityExample3().getEmail()).get();

        MeetingRequestEntity meetingRequestEntity1 = meetingRequestDataExample1().withDoctor(doctorEntity1).withPatient(patientEntity1);
        MeetingRequestEntity meetingRequestEntity2 = meetingRequestDataExample2().withDoctor(doctorEntity2).withPatient(patientEntity2);
        MeetingRequestEntity meetingRequestEntity3 = meetingRequestDataExample3().withDoctor(doctorEntity3).withPatient(patientEntity3);


        Set<MeetingRequestEntity> set = Set.of(meetingRequestEntity1, meetingRequestEntity2, meetingRequestEntity3);
        meetingRequestJpaRepository.saveAllAndFlush(set);
    }

    @Test
    void thatMeetingRequestsAreFoundByPatients() {
        //when
        List<MeetingRequestEntity> allByPatientEmail1 = meetingRequestJpaRepository.findAllByPatientEmail(patientExample3().getEmail());
        List<MeetingRequestEntity> allByPatientEmail2 = meetingRequestJpaRepository.findAllByPatientEmail(patientExample2().getEmail());
        //then
        Assertions.assertThat(allByPatientEmail1.size()).isEqualTo(1);
        Assertions.assertThat(allByPatientEmail2.size()).isEqualTo(1);

    }

    @Test
    void thatMeetingRequestsAreFoundByDoctors() {
        //when
        List<MeetingRequestEntity> allByDoctorEmail1 = meetingRequestJpaRepository.findAllByDoctorEmail(doctorExample1().getEmail());
        List<MeetingRequestEntity> allByDoctorEmail2 = meetingRequestJpaRepository.findAllByDoctorEmail(doctorEntityExample2().getEmail());
        //then
        Assertions.assertThat(allByDoctorEmail1.size()).isEqualTo(1);
        Assertions.assertThat(allByDoctorEmail2.size()).isEqualTo(1);

    }
}