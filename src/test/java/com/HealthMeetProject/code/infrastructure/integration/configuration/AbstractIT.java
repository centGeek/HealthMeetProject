package com.HealthMeetProject.code.infrastructure.integration.configuration;

import com.HealthMeetProject.code.HealthMeetProjectApplication;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(PersistenceContainerTestConfiguration.class)
@SpringBootTest(
        classes = HealthMeetProjectApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIT {
    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String basePath;
}
