package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.util.DoctorExampleData;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.HealthMeetProject.code.util.DoctorExampleData.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AvailabilityScheduleJpaRepositoryTest {
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;

    @Test
    void thatTermsAreFoundByDoctor() {
        //given
        List<AvailabilityScheduleEntity> availabilityScheduleEntities = List.of(availabilitySchedule1(), availabilitySchedule2(), availabilitySchedule3());
        availabilityScheduleJpaRepository.saveAllAndFlush(availabilityScheduleEntities);
        //when
        List<AvailabilityScheduleEntity> allTermsByGivenDoctor1 = availabilityScheduleJpaRepository.findAllTermsByGivenDoctor(doctorExample3().getEmail());
        List<AvailabilityScheduleEntity> allTermsByGivenDoctor2 = availabilityScheduleJpaRepository.findAllTermsByGivenDoctor(doctorExample1().getEmail());

        //then
        Assertions.assertEquals(allTermsByGivenDoctor1.size(), 3);
        Assertions.assertEquals(allTermsByGivenDoctor2.size(), 0);


    }


}