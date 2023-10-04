package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.NoteApiController;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class NoteApiControllerTest {

    @Mock
    private MeetingRequestService meetingRequestService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private NoteDAO noteDAO;

    @InjectMocks
    private NoteApiController noteApiController;


    @Test
    public void testGetNote() {
        //given
        Integer meetingId = 1;
        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setMeetingId(meetingId);
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        LocalDateTime visitStart = LocalDateTime.now();
        LocalDateTime visitEnd = LocalDateTime.now().plusHours(1);
        meetingRequest.setDoctor(doctor);
        meetingRequest.setPatient(patient);
        meetingRequest.setVisitStart(visitStart);
        meetingRequest.setVisitEnd(visitEnd);
        when(meetingRequestService.findById(meetingId)).thenReturn(meetingRequest);

        //when
        Note note = noteApiController.getNote(meetingId);

        //then
        assertEquals(meetingId, note.getNoteId());
        assertEquals(doctor, note.getDoctor());
        assertEquals(patient, note.getPatient());
        assertEquals(visitStart, note.getStartTime());
        assertEquals(visitEnd, note.getEndTime());
    }

    @Test
    public void testAddNote() {
        //given
        Integer meetingId = 1;
        String illness = "Test illness";
        String description = "Test description";
        MeetingRequest meetingRequest = new MeetingRequest();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        LocalDateTime visitStart = LocalDateTime.now();
        LocalDateTime visitEnd = LocalDateTime.now().plusHours(1);
        meetingRequest.setDoctor(doctor);
        meetingRequest.setPatient(patient);
        meetingRequest.setVisitStart(visitStart);
        meetingRequest.setVisitEnd(visitEnd);
        when(meetingRequestService.findById(meetingId)).thenReturn(meetingRequest);

        //when
        ResponseEntity<?> responseEntity = noteApiController.addNote(meetingId, illness, description);

        //then
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Sprawdź, czy metoda writeNote została wywołana z odpowiednimi argumentami
        verify(doctorService, times(1)).writeNote(doctor, illness, description, patient, visitStart, visitEnd);
    }

    @Test
    public void testGetIllnessHistory() {
        //given
        Integer meetingId = 1;
        MeetingRequest meetingRequest = new MeetingRequest();
        Patient patient = new Patient();
        meetingRequest.setPatient(patient);
        when(meetingRequestService.findById(meetingId)).thenReturn(meetingRequest);
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note();
        note1.setIllness("Illness 1");
        Note note2 = new Note();
        note2.setIllness("Illness 2");
        noteList.add(note1);
        noteList.add(note2);
        when(noteDAO.findByPatientEmail(patient.getEmail())).thenReturn(noteList);

        //when
        List<String> illnessHistory = noteApiController.getIllnessHistory(meetingId);

        //then
        assertEquals(2, illnessHistory.size());
        assertEquals("Illness 1", illnessHistory.get(0));
        assertEquals("Illness 2", illnessHistory.get(1));
    }
}
