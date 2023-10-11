package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.util.DoctorDTOFixtures;
import com.HealthMeetProject.code.util.DoctorExampleFixtures;
import com.HealthMeetProject.code.util.PatientExampleFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private final DoctorService doctorService;
    @MockBean
    private final DoctorDAO doctorDAO;
    @MockBean
    private final DoctorMapper doctorMapper;
    @MockBean
    private final AvailabilityScheduleService availabilityScheduleService;
    @MockBean
    private final PatientService patientService;
    @MockBean
    private final PatientDAO patientDAO;
    @Test
    public void testDoctorsPage() throws Exception {
        String email = PatientExampleFixtures.patientEntityExample1().getEmail();
        Mockito.when(patientService.authenticate()).thenReturn(email);
        Mockito.when(patientDAO.findByEmail(any())).thenReturn(PatientExampleFixtures.patientExample1());
        Mockito.when(doctorService.findAllAvailableDoctors()).thenReturn(List.of(DoctorExampleFixtures.doctorExample1(),
                        DoctorExampleFixtures.doctorExample3()));
        Mockito.when(doctorMapper.mapToDTO(DoctorExampleFixtures.doctorExample1())).thenReturn(DoctorDTOFixtures.getDoctorDTO1());
        Mockito.when(doctorMapper.mapToDTO(DoctorExampleFixtures.doctorExample3())).thenReturn(DoctorDTOFixtures.getDoctorDTO2());
        mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient"));
    }

    @Test
    public void testShowEmployeeDetails() throws Exception {
        Mockito.when(doctorDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(DoctorExampleFixtures.doctorExample1()));
        Mockito.when(doctorMapper.mapToDTO(any())).thenReturn(DoctorDTOFixtures.getDoctorDTO1());
        Mockito.when(availabilityScheduleService.findAllAvailableTermsByGivenDoctor(any())).thenReturn(List.of(DoctorExampleFixtures.availabilityScheduleDTO1()));
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/terms/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("availability_terms"));
    }

    @Test
    public void testShowEditDoctorForm() throws Exception {
        Mockito.when(doctorService.findById(Mockito.anyInt())).thenReturn(Optional.of(DoctorExampleFixtures.doctorExample1()));
        Mockito.when(doctorMapper.mapToDTO(any())).thenReturn(DoctorDTOFixtures.getDoctorDTO1());
        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit-doctor"));
    }

    @Test
    public void testUpdateDoctor() throws Exception {
        Mockito.when(doctorService.findById(Mockito.anyInt())).thenReturn(Optional.of(DoctorExampleFixtures.doctorExample1()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/doctor/1/edit"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/logout"));
    }
}
