package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueReceiptDTO {
    private List<MedicineDTO> medicineDTOList;
    private Patient patient;
    private Doctor doctor;
}
