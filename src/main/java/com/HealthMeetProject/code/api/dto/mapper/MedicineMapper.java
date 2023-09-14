package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface MedicineMapper {
    Medicine map(MedicineDTO medicine);
}
