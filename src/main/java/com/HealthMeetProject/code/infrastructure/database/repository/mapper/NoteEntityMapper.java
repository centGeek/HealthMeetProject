package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface NoteEntityMapper {
    NoteEntity mapToEntity(Note note);
    Note mapFromEntity(NoteEntity note);
}
