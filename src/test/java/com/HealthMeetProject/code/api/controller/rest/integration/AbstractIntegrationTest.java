package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.HealthMeetProjectApplication;
import com.HealthMeetProject.code.infrastructure.database.repository.MeetingRequestRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.*;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
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
    private MeetingRequestJpaRepository meetingRequestRepository;

    @Autowired
    private RoleRepository roleRepository;


    @BeforeEach
    public void after() {
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
        meetingRequestRepository.deleteAll();
        roleRepository.saveAndFlush(RoleEntity.builder().id(1).role("DOCTOR").build());
        roleRepository.saveAndFlush(RoleEntity.builder().id(2).role("PATIENT").build());

    }
}