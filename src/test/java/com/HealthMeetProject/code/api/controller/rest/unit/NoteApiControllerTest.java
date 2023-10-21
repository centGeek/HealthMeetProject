package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.NoteApiController;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Mock
    private PatientDAO patientDAO;

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
        when(meetingRequestService.restFindById(meetingId)).thenReturn(meetingRequest);

        //when
        Note note = noteApiController.getNote(meetingId, "twoja noga");

        //then
        assertEquals(meetingId, note.getNoteId());
        assertEquals(visitStart, note.getStartTime());
        assertEquals(visitEnd, note.getEndTime());
    }

    @Test
    public void testAddNote() {
        //given
        Integer meetingId = 1;
        String illness = "Test illness";
        Note note = NoteExampleFixtures.noteExample1();
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
        when(meetingRequestService.restFindById(meetingId)).thenReturn(meetingRequest);

        //when
        ResponseEntity<?> responseEntity = noteApiController.addNote(meetingId, note);

        //then
        //noinspection deprecation
        assertEquals(201, responseEntity.getStatusCodeValue());

        verify(doctorService, times(1)).writeNote(note.getDoctor(), note.getIllness(), note.getDescription(),
                note.getPatient(), meetingRequest.getVisitStart(), meetingRequest.getVisitEnd());
    }

    @Test
    public void testGetIllnessHistory() {
        //given
        Integer meetingId = 1;
        MeetingRequest meetingRequest = new MeetingRequest();
        Patient patient = PatientExampleFixtures.patientExample1();
        patient.setPatientId(4);
        meetingRequest.setPatient(patient);
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note();
        note1.setIllness("Illness 1");
        Note note2 = new Note();
        note2.setIllness("Illness 2");
        noteList.add(note1);
        noteList.add(note2);
        when(noteDAO.findByPatientEmail(patient.getEmail())).thenReturn(noteList);
        when(patientDAO.findByEmail(patient.getEmail())).thenReturn(patient);

        //when
        List<String> illnessHistory = noteApiController.getIllnessHistory(patient.getEmail()).getIllnessList();

        //then
        assertEquals(2, illnessHistory.size());
        assertEquals("Illness 1", illnessHistory.get(0));
        assertEquals("Illness 2", illnessHistory.get(1));
    }
}
