package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.HealthMeetProjectApplication;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Import(PersistenceContainerTestConfiguration.class)
@SpringBootTest(
        classes = {HealthMeetProjectApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

}