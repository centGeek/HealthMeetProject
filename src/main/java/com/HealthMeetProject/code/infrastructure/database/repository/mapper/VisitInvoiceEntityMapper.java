package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.VisitInvoice;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.VisitInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitInvoiceEntityMapper {


    VisitInvoice mapFromEntity(final VisitInvoiceEntity visitInvoiceEntity);
    VisitInvoiceEntity mapToEntity(final VisitInvoice visitInvoice);
}
