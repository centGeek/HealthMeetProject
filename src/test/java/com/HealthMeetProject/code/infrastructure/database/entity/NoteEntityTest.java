package com.HealthMeetProject.code.infrastructure.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class NoteEntityTest {
    @Test
    public void noteEntityTest(){
        NoteEntity noteEntity1 = new NoteEntity();
        noteEntity1.setNoteId(3);
        noteEntity1.setDescription("zupaGrzybowa");
        noteEntity1.setIllness("kapusta");
        LocalDateTime now = LocalDateTime.now();
        noteEntity1.setStartTime(now);
        noteEntity1.setEndTime(now);

        NoteEntity noteEntity2 = new NoteEntity();
        noteEntity2.setNoteId(3);
        noteEntity2.setDescription("zupaGrzybowa");
        noteEntity2.setIllness("kapusta");
        noteEntity2.setStartTime(now);
        noteEntity2.setEndTime(now);
        Assertions.assertEquals(noteEntity1, noteEntity2);
        Assertions.assertEquals(noteEntity1.hashCode(), noteEntity2.hashCode());
    }
}