package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.NoteEntityMapper;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteRepositoryTest {

    @Mock
    private NoteJpaRepository noteJpaRepository;

    @Mock
    private NoteEntityMapper noteEntityMapper;

    @InjectMocks
    private NoteRepository noteRepository;

    @Test
    public void testIsThereNoteWithTheSameTimeVisitAndDoctor_True() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        String email = "doctor@example.com";
        NoteEntity noteEntity = NoteExampleFixtures.noteEntityExample1();

        when(noteJpaRepository.isThereNoteWithTheSameTimeVisitAndDoctor(startTime, endTime, email))
                .thenReturn(noteEntity);

        boolean result = noteRepository.isThereNoteWithTheSameTimeVisitAndDoctor(startTime, endTime, email);

        assertTrue(result);
    }

    @Test
    public void testIsThereNoteWithTheSameTimeVisitAndDoctor_False() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        String email = "doctor@example.com";

        when(noteJpaRepository.isThereNoteWithTheSameTimeVisitAndDoctor(startTime, endTime, email))
                .thenReturn(null);

        boolean result = noteRepository.isThereNoteWithTheSameTimeVisitAndDoctor(startTime, endTime, email);

        assertFalse(result);
    }

    @Test
    public void testFindByPatientEmail() {
        String patientEmail = "j.kowalski@gmail.com";
        List<NoteEntity> noteEntities = List.of(NoteExampleFixtures.noteEntityExample1());
        when(noteJpaRepository.findByPatientEmail(patientEmail)).thenReturn(noteEntities);
        when(noteEntityMapper.mapFromEntity(NoteExampleFixtures.noteEntityExample1())).thenReturn(NoteExampleFixtures.noteExample1());

        List<Note> result = noteRepository.findByPatientEmail(patientEmail);

        assertEquals(noteEntities.size(), result.size());
    }

}