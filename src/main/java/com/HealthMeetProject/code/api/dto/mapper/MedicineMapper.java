package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.domain.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface MedicineMapper {
    Medicine mapFromDTO(MedicineDTO medicine);
}
