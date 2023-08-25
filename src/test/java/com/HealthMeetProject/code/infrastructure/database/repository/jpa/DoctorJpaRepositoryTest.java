package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.HealthMeetProject.code.util.DoctorExampleData.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorJpaRepositoryTest {

    private DoctorJpaRepository doctorJpaRepository;

    public void given() {
        var doctors = List.of(doctorExample1(), doctorExample2(), doctorExample3());
        doctorJpaRepository.saveAllAndFlush(doctors);
    }

    @Test
    void thatDoctorsCanBeSaveAndListedCorrectly() {
        given();
        //when
        List<DoctorEntity> allAvailableDoctors = doctorJpaRepository.findAll();
        //then
        Assertions.assertEquals(allAvailableDoctors.size(), 3);
    }

    @Test
    void thatDoctorsAreFoundBySpecialization() {
        given();

        //when
        List<DoctorEntity> allBySpecialization = doctorJpaRepository.findAllBySpecialization(Specialization.PSYCHIATRIST);

        //then
        Assertions.assertEquals(allBySpecialization.size(), 2);
    }

    @Test
    void thatDoctorIsFoundByEmail(){
        given();
        //when
        Optional<DoctorEntity> byEmail = doctorJpaRepository.findByEmail(doctorExample1().getEmail());
        //then
        Assertions.assertEquals(byEmail.get().getEmail(), "j.kowalski@gmail.com");
    }

}