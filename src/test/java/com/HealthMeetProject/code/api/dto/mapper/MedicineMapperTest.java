package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.domain.Medicine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MedicineMapperTest {
    private MedicineMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(MedicineMapper.class);
    }

    @Test
    public void shouldMapMedicineDTOToMedicine() {
        // given
        MedicineDTO medicineDTO = new MedicineDTO();
        medicineDTO.setName("Ibuprofen");
        medicineDTO.setApproxPrice(BigDecimal.TEN);
        medicineDTO.setQuantity(3);
        //when
        Medicine medicine = mapper.mapFromDTO(medicineDTO);
        //then
        assertions(medicineDTO, medicine);
        Assertions.assertThat(medicineDTO.getName()).isEqualTo(medicine.getName());
        Assertions.assertThat(medicine.getReceipt()).isEqualTo(null);
    }

    private static void assertions(MedicineDTO medicineDTO, Medicine medicine) {
        assertNull(medicine.getMedicineId());
        assertEquals(medicineDTO.getName(), medicine.getName());
        assertEquals(medicineDTO.getQuantity(), medicine.getQuantity());
        assertEquals(medicineDTO.getName(), medicine.getName());
        assertEquals(medicineDTO.getApproxPrice(), medicine.getApproxPrice());
        assertNull(medicine.getReceipt());
    }
}