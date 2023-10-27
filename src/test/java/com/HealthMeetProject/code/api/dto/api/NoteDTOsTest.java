package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.api.dto.api.NoteDTOs;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NoteDTOsTest {

    @Test
    public void testNoArgsConstructor() {
        NoteDTOs noteDTOs =
                NoteDTOs.of(List.of(NoteExampleFixtures.noteExample1()));
        assertNotNull(noteDTOs);
        assertNotNull(noteDTOs.getNoteList());
        assertEquals(1, noteDTOs.getNoteList().size());
    }

    @Test
    public void testAllArgsConstructor() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note());
        noteList.add(new Note());
        NoteDTOs noteDTOs = NoteDTOs.of(noteList);
        assertNotNull(noteDTOs);
        assertEquals(noteList, noteDTOs.getNoteList());
    }

    @Test
    public void testBuilder() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note());
        noteList.add(new Note());
        NoteDTOs noteDTOs = NoteDTOs.builder()
                .noteList(noteList)
                .build();
        assertNotNull(noteDTOs);
        assertEquals(noteList, noteDTOs.getNoteList());
    }

    @Test
    public void testStaticFactoryMethod() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note());
        noteList.add(new Note());
        NoteDTOs noteDTOs = NoteDTOs.of(noteList);
        assertNotNull(noteDTOs);
        assertEquals(noteList, noteDTOs.getNoteList());
    }

    @Test
    public void testEqualsAndHashCode() {
        List<Note> noteList1 = new ArrayList<>();
        noteList1.add(new Note());
        List<Note> noteList2 = new ArrayList<>();
        noteList2.add(new Note());

        NoteDTOs noteDTOs1 = NoteDTOs.of(noteList1);
        NoteDTOs noteDTOs2 = NoteDTOs.of(noteList2);

        assertEquals(noteDTOs1, noteDTOs2);
        assertEquals(noteDTOs1.hashCode(), noteDTOs2.hashCode());
    }

    @Test
    public void testToString() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note());
        NoteDTOs noteDTOs = NoteDTOs.of(noteList);
        String expectedToString = "NoteDTOs(noteList=" + noteList + ")";
        assertEquals(expectedToString, noteDTOs.toString());
    }
}
