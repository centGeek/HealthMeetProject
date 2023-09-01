package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.configuration.PersistenceContainerTestConfiguration;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static com.HealthMeetProject.code.util.ReceiptExampleFixtures.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiptJpaRepositoryTest {
    private final ReceiptJpaRepository receiptJpaRepository;
    @Test
    void thatReceiptsAreFoundByClient(){
        //given
        Set<ReceiptEntity> receiptEntities = Set.of(receiptExampleData1(), receiptExampleData2(), receiptExampleData3());
        receiptJpaRepository.saveAllAndFlush(receiptEntities);
        //when
        List<ReceiptEntity> patientReceipts = receiptJpaRepository.findPatientReceipts(PatientExampleFixtures.patientExample1().getEmail());
        //then
        assertThat(patientReceipts.size()).isEqualTo(2);
    }
}