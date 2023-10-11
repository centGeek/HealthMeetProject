package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ReceiptEntityMapper {
    @Mapping(target = "medicine", ignore = true)
    @Mapping(source = "doctor.meetingRequests", target = "doctor.meetingRequests", ignore = true)

    ReceiptEntity mapToEntity(Receipt receipt);

    @Mapping(target = "medicine", ignore = true)
    @Mapping(source = "doctor.meetingRequests", target = "doctor.meetingRequests", ignore = true)

    Receipt mapFromEntity(ReceiptEntity receipt);

}
