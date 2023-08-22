package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepostitory  extends JpaRepository<Integer, NoteEntity> {
   

}
