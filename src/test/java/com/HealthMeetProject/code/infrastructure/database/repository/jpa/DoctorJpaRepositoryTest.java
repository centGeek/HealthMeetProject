package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.HealthMeetProject.code.util.DoctorExampleData.doctorExample1;
import static com.HealthMeetProject.code.util.DoctorExampleData.doctorExample2;
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorJpaRepositoryTest {
    private DoctorJpaRepository doctorJpaRepository;

    @Test
    void thatDoctorsCanBeSaveAndListedCorrectly(){
        //given

        var doctors = List.of(doctorExample1(), doctorExample2());
        doctorJpaRepository.deleteAll();
        doctorJpaRepository.saveAllAndFlush(doctors);
        //when
        List<DoctorEntity> allAvailableDoctors = doctorJpaRepository.findAll();
        //then
        Assertions.assertEquals(allAvailableDoctors.size(), 2);

    }
}