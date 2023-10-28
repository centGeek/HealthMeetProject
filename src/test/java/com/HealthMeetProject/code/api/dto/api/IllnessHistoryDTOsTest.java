package com.HealthMeetProject.code.api.dto.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IllnessHistoryDTOsTest {

    private IllnessHistoryDTOs illnessHistoryDTOs1;
    private IllnessHistoryDTOs illnessHistoryDTOs2;

    @BeforeEach
    void setUp() {
        List<String> illnessList1 = Arrays.asList("Illness1", "Illness2");
        List<String> illnessList2 = Arrays.asList("Illness1", "Illness2");
        illnessHistoryDTOs1 = IllnessHistoryDTOs.of(illnessList1);
        illnessHistoryDTOs2 = IllnessHistoryDTOs.of(illnessList2);
    }

    @Test
    void testGetIllnessList() {
        //given
        List<String> expectedIllnessList = Arrays.asList("Illness1", "Illness2");
        //when,then
        assertEquals(expectedIllnessList, illnessHistoryDTOs1.getIllnessList());
    }

    @Test
    void testSetIllnessList() {
        //given
        List<String> newIllnessList = new ArrayList<>(Arrays.asList("NewIllness1", "NewIllness2"));
        //when
        illnessHistoryDTOs1.setIllnessList(newIllnessList);
        //then
        assertEquals(newIllnessList, illnessHistoryDTOs1.getIllnessList());
    }

    @Test
    void testBuilder() {
        //given
        IllnessHistoryDTOs newIllnessHistoryDTOs = IllnessHistoryDTOs.builder()
                .illnessList(Arrays.asList("IllnessA", "IllnessB"))
                .build();
        //when, then
        assertNotNull(newIllnessHistoryDTOs);
        assertTrue(newIllnessHistoryDTOs.getIllnessList().contains("IllnessA"));
        assertTrue(newIllnessHistoryDTOs.getIllnessList().contains("IllnessB"));
    }

    @Test
    void testEquals() {
        assertEquals(illnessHistoryDTOs1, illnessHistoryDTOs2);
    }

    @Test
    void testHashCode() {
        assertEquals(illnessHistoryDTOs1.hashCode(), illnessHistoryDTOs2.hashCode());
    }

}
