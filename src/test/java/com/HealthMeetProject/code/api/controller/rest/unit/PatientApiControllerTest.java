package com.HealthMeetProject.code.api.controller.rest.unit;

import com.HealthMeetProject.code.api.controller.rest.PatientApiController;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.mapper.PatientMapper;
import com.HealthMeetProject.code.business.PatientHistoryDTOService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientApiControllerTest {

    @Mock
    private MeetingRequestDAO meetingRequestDAO;
    @Mock
    private PatientHistoryDTOService patientHistoryDTOService;
    @Mock
    private PatientService patientService;
    @Mock
    private PatientDAO patientDAO;
    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientApiController patientApiController;


    @Test
    public void testRegisterPatient() {
        //given
        Patient patient = new Patient();
        PatientDTO patientDTO = new PatientDTO();
        when(patientMapper.mapToDTO(patient)).thenReturn(patientDTO);

        //when
        ResponseEntity<PatientDTO> responseEntity = patientApiController.registerPatient(patient);

        //then
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Sprawdź, czy metoda register została wywołana z odpowiednimi argumentami
        verify(patientService, times(1)).register(patientDTO);
    }

    @Test
    public void testGetPatientById() {
        //given
        Integer patientId = 1;
        Patient patient = new Patient();
        when(patientDAO.findById(patientId)).thenReturn(patient);
        PatientDTO patientDTO = new PatientDTO();
        when(patientMapper.mapToDTO(patient)).thenReturn(patientDTO);

        //when
        PatientDTO resultDTO = patientApiController.getPatientById(patientId);

        //then
        assertEquals(patientDTO, resultDTO);
    }
}
