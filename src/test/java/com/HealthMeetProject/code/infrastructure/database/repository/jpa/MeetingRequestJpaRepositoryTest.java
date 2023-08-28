package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.util.MeetingRequestsData;
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

import static com.HealthMeetProject.code.util.DoctorExampleData.doctorExample1;
import static com.HealthMeetProject.code.util.DoctorExampleData.doctorExample2;
import static com.HealthMeetProject.code.util.MeetingRequestsData.*;
import static com.HealthMeetProject.code.util.PatientExampleData.patientExample2;
import static com.HealthMeetProject.code.util.PatientExampleData.patientExample3;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingRequestJpaRepositoryTest {

    private final MeetingRequestJpaRepository meetingRequestJpaRepository;
    @BeforeEach
    public void given() {
        Set<MeetingRequestEntity> meetingRequests = Set.of(meetingRequestDataExample1(), meetingRequestDataExample2(),
                meetingRequestDataExample3(), meetingRequestDataExample4());
        meetingRequestJpaRepository.saveAllAndFlush(meetingRequests);
    }

    @Test
    void thatMeetingRequestsAreFoundByPatients() {
        //when
        List<MeetingRequestEntity> allByPatientEmail1 = meetingRequestJpaRepository.findAllByPatientEmail(patientExample3().getEmail());
        List<MeetingRequestEntity> allByPatientEmail2 = meetingRequestJpaRepository.findAllByPatientEmail(patientExample2().getEmail());
        //then
        Assertions.assertThat(allByPatientEmail1.size()).isEqualTo(2);
        Assertions.assertThat(allByPatientEmail2.size()).isEqualTo(1);

    }

    @Test
    void thatMeetingRequestsAreFoundByDoctors() {
        //when
        List<MeetingRequestEntity> allByDoctorEmail1 = meetingRequestJpaRepository.findAllByDoctorEmail(doctorExample1().getEmail());
        List<MeetingRequestEntity> allByDoctorEmail2 = meetingRequestJpaRepository.findAllByDoctorEmail(doctorExample2().getEmail());
        //then
        Assertions.assertThat(allByDoctorEmail1.size()).isEqualTo(1);
        Assertions.assertThat(allByDoctorEmail2.size()).isEqualTo(3);

    }
}