package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteDAO {

    List<Note> findByPatientEmail(String email);

    Boolean isThereNoteWithTheSameTimeVisitAndDoctor(LocalDateTime startTime, LocalDateTime endTime, String email);

}
