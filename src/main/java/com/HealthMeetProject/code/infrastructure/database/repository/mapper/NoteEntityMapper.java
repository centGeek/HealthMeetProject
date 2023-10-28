package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface NoteEntityMapper {
    @Mapping(source = "doctor.meetingRequests", target = "doctor.meetingRequests", ignore = true)
    NoteEntity mapToEntity(Note note);

    @Mapping(source = "doctor.meetingRequests", target = "doctor.meetingRequests", ignore = true)
    Note mapFromEntity(NoteEntity note);
}
