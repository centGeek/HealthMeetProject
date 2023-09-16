package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessingMoneyServiceTest {

    private ProcessingMoneyService processingMoneyService;

    @BeforeEach
    void setUp() {
        processingMoneyService = new ProcessingMoneyService();
    }

    @Test
    void testSumTotalCosts_NoRequests_NoMedicines() {
        List<MeetingRequest> emptyRequests = new ArrayList<>();
        List<MedicineEntity> emptyMedicines = new ArrayList<>();

        BigDecimal totalCost = processingMoneyService.sumTotalCosts(emptyRequests, emptyMedicines);

        assertEquals(BigDecimal.ZERO, totalCost);
    }

    @Test
    void testSumTotalCosts_WithRequests_NoMedicines() {
        List<MeetingRequest> requests = new ArrayList<>();
        requests.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());

        List<MedicineEntity> emptyMedicines = new ArrayList<>();

        BigDecimal totalCost = processingMoneyService.sumTotalCosts(requests, emptyMedicines);

        assertEquals(BigDecimal.ONE, totalCost);
    }

    @Test
    void testSumTotalCosts_NoRequests_WithMedicines() {
        List<MeetingRequest> emptyRequests = new ArrayList<>();
        List<MedicineEntity> medicines = new ArrayList<>();
        medicines.add(MedicineExampleFixtures.medicineEntityExampleData1()); // Assuming MedicineEntity constructor takes approxPrice

        BigDecimal totalCost = processingMoneyService.sumTotalCosts(emptyRequests, medicines);

        assertEquals(new BigDecimal("500"), totalCost);
    }

    @Test
    void testSumTotalCosts_WithRequests_And_Medicines() {
        List<MeetingRequest> requests = new ArrayList<>();
        requests.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());

        List<MedicineEntity> medicines = new ArrayList<>();
        medicines.add(MedicineExampleFixtures.medicineEntityExampleData1());

        BigDecimal totalCost = processingMoneyService.sumTotalCosts(requests, medicines);

        assertEquals(new BigDecimal("501"), totalCost);
    }
}
