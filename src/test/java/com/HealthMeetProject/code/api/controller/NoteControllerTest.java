package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.MeetingRequestService;
import com.HealthMeetProject.code.business.dao.NoteDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
import com.HealthMeetProject.code.util.NoteExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private final MeetingRequestService meetingRequestService;
    @MockBean
    private final DoctorService doctorService;
    @MockBean
    private final NoteDAO noteDAO;
    @Test
    void thatNotePageIsLoadedCorrectly() throws Exception {
        Integer meetingId = 4;

        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();

        when(meetingRequestService.findById(meetingId)).thenReturn(meetingRequest);

        mockMvc.perform(get("/doctor/issue/note/{meetingId}", meetingId))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("meetingId"))
                .andExpect(model().attributeExists("doctor"))
                .andExpect(view().name("note"));

        verify(meetingRequestService, times(1)).findById(meetingId);
    }

    @Test
    public void testGetNotePageMeetingNotFound() throws Exception {
        Integer meetingId = 1;
        String email = "patient@email.com";
        when(noteDAO.findByPatientEmail(email)).thenReturn(List.of(NoteExampleFixtures.noteExample1()));
        mockMvc.perform(get("/note/{meetingId}", email))
                .andExpect(status().isNotFound());

    }

    @Test
    void thatNoteIsAddedSuccessfully() throws Exception {
        Integer meetingId = 4;

        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setMeetingId(meetingId);
        meetingRequest.setDoctor(new Doctor());
        meetingRequest.setPatient(new Patient());
        meetingRequest.setVisitStart(LocalDateTime.now());
        meetingRequest.setVisitEnd(LocalDateTime.now().plusHours(1));

        when(meetingRequestService.findById(meetingId)).thenReturn(meetingRequest);

        mockMvc.perform(post(NoteController.NOTE_PAGE_ADD, meetingId)
                        .param("illness", "Some Illness")
                        .param("description", "Description of the illness"))
                .andExpect(redirectedUrl("/doctor"));

        verify(doctorService, times(1)).writeNote(eq(meetingRequest.getDoctor()),
                eq("Some Illness"), eq("Description of the illness"), eq(meetingRequest.getPatient()),
                eq(meetingRequest.getVisitStart()), eq(meetingRequest.getVisitEnd()));
    }
}
