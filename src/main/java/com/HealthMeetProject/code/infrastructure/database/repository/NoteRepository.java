package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.NoteEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class NoteRepository implements NoteDAO {
    private final NoteJpaRepository noteJpaRepository;
    private final NoteEntityMapper noteEntityMapper;
    @Override
   public Boolean isThereNoteWithTheSameTimeVisitAndDoctor(LocalDateTime startTime, LocalDateTime endTime, String email){
        NoteEntity thereNoteWithTheSameTimeVisitAndDoctor = noteJpaRepository.isThereNoteWithTheSameTimeVisitAndDoctor(startTime, endTime, email);
        return !Objects.isNull(thereNoteWithTheSameTimeVisitAndDoctor);
    }
    @Override
    public List<Note> findByPatientEmail(String patientEmail){
       return noteJpaRepository.findByPatientEmail(patientEmail).stream().map(noteEntityMapper::mapFromEntity).toList();
    };

}
