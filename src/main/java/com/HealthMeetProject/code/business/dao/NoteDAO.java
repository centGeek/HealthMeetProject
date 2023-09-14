package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Note;

import java.time.OffsetDateTime;
import java.util.List;

public interface NoteDAO {

    List<Note> findByPatientEmail(String email);

    Boolean isThereNoteWithTheSameTimeVisitAndDoctor(OffsetDateTime startTime, OffsetDateTime endTime, String email);

}
