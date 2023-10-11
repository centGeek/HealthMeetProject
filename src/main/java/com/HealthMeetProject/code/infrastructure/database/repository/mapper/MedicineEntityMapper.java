package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineEntityMapper {
    @Mapping(target = "receipt", ignore = true)

    MedicineEntity mapToEntity(Medicine medicine);
    @Mapping(target = "receipt", ignore = true)

    Medicine mapFromEntity(MedicineEntity medicineEntity);
}
