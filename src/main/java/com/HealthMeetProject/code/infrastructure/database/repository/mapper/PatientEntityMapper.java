package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {
    Patient mapFromEntity(final PatientEntity patientEntity);

    @SuppressWarnings("unused")
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
