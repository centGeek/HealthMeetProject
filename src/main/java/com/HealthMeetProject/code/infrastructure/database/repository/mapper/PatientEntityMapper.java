package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.*;
import com.HealthMeetProject.code.infrastructure.database.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {
    @Mapping(source = "invoices",target = "invoices", qualifiedByName = "mapInvoices")
    @Mapping(source = "receipts",target = "receipts", qualifiedByName = "mapReceipts")
    Patient mapFromEntity(final PatientEntity patientEntity);
    @Named("mapInvoices")
    @SuppressWarnings("unused")
    default Set<VisitInvoice> mapInvoices(Set<VisitInvoiceEntity> invoiceEntities) {
        return invoiceEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Named("mapReceipts")
    @SuppressWarnings("unused")
    default Set<Receipt> mapReceipts(Set<ReceiptEntity> receiptEntities) {
        return receiptEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    VisitInvoice mapFromEntity(VisitInvoiceEntity entity);
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    Note mapFromEntity(NoteEntity entity);
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    Receipt mapFromEntity(ReceiptEntity entity);
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    MeetingRequest mapFromEntity(MeetingRequestEntity entity);

    PatientEntity mapToEntity(final Patient patient);
}
