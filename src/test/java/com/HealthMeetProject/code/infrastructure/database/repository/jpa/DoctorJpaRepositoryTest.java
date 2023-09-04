package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
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

import static com.HealthMeetProject.code.util.DoctorExampleFixtures.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorJpaRepositoryTest {

    private DoctorJpaRepository doctorJpaRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void given() {
        //given for every test
        var doctors = List.of(doctorEntityExample1(), doctorEntityExample2(), doctorEntityExample3());
        doctorJpaRepository.saveAllAndFlush(doctors);
    }

    @Test
    void thatDoctorsCanBeSaveAndListedCorrectly() {
        //when
        List<DoctorEntity> allAvailableDoctors = doctorJpaRepository.findAll();
        //then
        Assertions.assertEquals(allAvailableDoctors.size(), 6);
    }

    @Test
    void thatDoctorsAreFoundBySpecialization() {
        //when
        List<DoctorEntity> allBySpecialization = doctorJpaRepository.findAllBySpecialization(Specialization.PSYCHIATRIST);

        //then
        Assertions.assertEquals(allBySpecialization.size(), 2);
    }

    @Test
    void thatDoctorIsFoundByEmail() {
        //when
        DoctorEntity byEmail = doctorJpaRepository.findByEmail(doctorExample1().getEmail()).get();
        //then
        Assertions.assertEquals(byEmail.getEmail(), doctorExample1().getEmail());
    }

}