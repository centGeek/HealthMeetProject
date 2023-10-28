package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteJpaRepository extends JpaRepository<NoteEntity, Integer> {


    @Query("""
                select note from NoteEntity note where note.patient.email =:patientEmail
            """)
    List<NoteEntity> findByPatientEmail(@Param("patientEmail") String patientEmail);

    @Query("""
                    select note from NoteEntity note where note.startTime = :startTime and note.endTime =:endTime and note.doctor.email =:email
            """)
    NoteEntity isThereNoteWithTheSameTimeVisitAndDoctor(@Param("startTime") LocalDateTime startTime,
                                                        @Param("endTime") LocalDateTime endTime, @Param("email") String email);
}
