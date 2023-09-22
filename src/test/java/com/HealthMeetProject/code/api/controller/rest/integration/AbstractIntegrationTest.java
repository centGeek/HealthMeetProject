package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.HealthMeetProjectApplication;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;


@Import(PersistenceContainerTestConfiguration.class)
@SpringBootTest(
        classes = {HealthMeetProjectApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public abstract class AbstractIntegrationTest {
    @Autowired
    private DoctorJpaRepository doctorRepository;
    @Autowired
    private PatientJpaRepository patientRepository;
    @Autowired
    private RoleRepository roleRepository;
//
//    @Autowired
//    private PatientJpaRepository patientJpaRepository;
//    @Autowired
//    private ReceiptJpaRepository repository;

    @BeforeEach
    public void after() {
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
        roleRepository.findById(10L);
    }
}