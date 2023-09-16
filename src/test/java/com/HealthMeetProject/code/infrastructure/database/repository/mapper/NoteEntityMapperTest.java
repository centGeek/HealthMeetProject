package com.HealthMeetProject.code.infrastructure.database.repository.mapper;

import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteEntityMapperTest {
    private NoteEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(NoteEntityMapper.class);
    }

    @Test
    public void shouldMapNoteEntityToNote() {
        // given
        NoteEntity noteEntity = NoteEntity.builder()
                .noteId(1)
                .description("You have a cold")
                .illness("flu")
                .doctor(DoctorExampleFixtures.doctorEntityExample1())
                .patient(PatientExampleFixtures.patientEntityExample1())
                .startTime(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 0), ZoneOffset.UTC))
                .endTime(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 0), ZoneOffset.UTC))
                .build();
        //when
        Note note = mapper.mapFromEntity(noteEntity);

        // then
        assertions(note,noteEntity);
    }

    @Test
    public void shouldMapNoteToNoteEntity() {
        // given
        Note note = Note.builder()
                .noteId(1)
                .description("You have a cold")
                .illness("flu")
                .doctor(DoctorExampleFixtures.doctorExample1())
                .patient(PatientExampleFixtures.patientExample1())
                .startTime(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 0), ZoneOffset.UTC))
                .endTime(OffsetDateTime.of(LocalDateTime.of(2020, 3, 2, 10, 0), ZoneOffset.UTC))
                .build();
        //when
        NoteEntity noteEntity = mapper.mapToEntity(note);
        //then
        assertions(note, noteEntity);
    }

    private static void assertions(Note note, NoteEntity noteEntity) {
        assertEquals(note.getNoteId(), noteEntity.getNoteId());
        assertEquals(note.getDescription(), noteEntity.getDescription());
        assertEquals(note.getIllness(), noteEntity.getIllness());
        assertEquals(note.getEndTime(), noteEntity.getEndTime());
        assertEquals(note.getStartTime(), noteEntity.getStartTime());
        assertEquals(note.getDoctor().getEmail(), noteEntity.getDoctor().getEmail());
        assertEquals(note.getPatient().getEmail(), noteEntity.getPatient().getEmail());
    }
}