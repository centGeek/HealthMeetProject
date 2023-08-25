package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteJpaRepository extends JpaRepository<NoteEntity, Integer> {


    @Query("""
    select note from NoteEntity note where note.patient.email =:patientEmail
""")
    List<NoteEntity> findByPatientEmail(@Param("patientEmail") String patientEmail);
}
