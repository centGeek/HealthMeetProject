package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.business.*;
import com.HealthMeetProject.code.business.dao.MedicineDAO;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.business.dao.ReceiptDAO;
import com.HealthMeetProject.code.domain.Address;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.util.*;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerTest {


    private MockMvc mockMvc;
    @MockBean
    private final MeetingRequestDAO meetingRequestDAO;
    @MockBean
    private final PatientService patientService;
    @MockBean
    private final PatientDAO patientDAO;
    @MockBean
    private final PatientHistoryDTOService patientHistoryDTOService;


//    @Test
//    void testGetPatientHistoryPage() throws Exception {
//        // Arrange
//        String email = "patient@example.com";
//
//        List<MeetingRequest> allUpcomingVisits = new ArrayList<>();
//        allUpcomingVisits.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
//        List<Boolean> canCancelMeetingList = new ArrayList<>();
//        canCancelMeetingList.add(true);
//
//        List<MeetingRequest> allCompletedServiceRequestsByEmail = new ArrayList<>();
//        allCompletedServiceRequestsByEmail.add(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
//
//        List<MedicineEntity> medicines = new ArrayList<>();
//        medicines.add(MedicineExampleFixtures.medicineExampleData1());
//        BigDecimal totalCosts = BigDecimal.valueOf(100.00);
//
//        List<NoteEntity> byPatientEmail = new ArrayList<>();
//        byPatientEmail.add(NoteExampleFixtures.noteEntityExample1());
//
//        List<Receipt> receipts = new ArrayList<>();
//        ReceiptExampleFixtures.receiptExampleData1().setDoctor(DoctorExampleFixtures.doctorExample1());
//        ReceiptExampleFixtures.receiptExampleData1().setPatient(PatientExampleFixtures.patientExample1());
//        receipts.add(ReceiptExampleFixtures.receiptExampleData1());
//
//        List<MedicineEntity> medicinesFromLastVisit = new ArrayList<>();
//        medicinesFromLastVisit.add(MedicineExampleFixtures.medicineExampleData2());
//
//        when(patientService.authenticate()).thenReturn(email);
//        when(meetingRequestDAO.findAllUpcomingVisits(email)).thenReturn(allUpcomingVisits);
//        when(patientService.canCancelMeetingList(allUpcomingVisits)).thenReturn(canCancelMeetingList);
//        when(meetingRequestService.findAllCompletedServiceRequestsByEmail(email)).thenReturn(allCompletedServiceRequestsByEmail);
//        when(medicineDAO.findByPatientEmail(email)).thenReturn(medicines);
//        when(processingMoneyService.sumTotalCosts(allCompletedServiceRequestsByEmail, medicines)).thenReturn(totalCosts);
//        when(noteJpaRepository.findByPatientEmail(email)).thenReturn(byPatientEmail);
//        when(receiptDAO.findPatientReceipts(email)).thenReturn(receipts);
//        when(receiptService.getMedicinesFromLastVisit(receipts)).thenReturn(medicinesFromLastVisit);
//
//
//        //then
//        mockMvc.perform(get(PatientController.PATIENT_HISTORY))
//                .andExpect(status().isOk())
//                .andExpect(view().name("patient_history"))
//                .andExpect(model().attribute("allUpcomingVisits", allUpcomingVisits))
//                .andExpect(model().attribute("canCancelMeetingList", canCancelMeetingList))
//                .andExpect(model().attribute("allCompletedServiceRequestsByEmail", allCompletedServiceRequestsByEmail))
//                .andExpect(model().attribute("byPatientEmail", byPatientEmail))
//                .andExpect(model().attribute("totalCosts", totalCosts))
//                .andExpect(model().attribute("receipts", receipts))
//                .andExpect(model().attribute("medicinesFromLastVisit", medicinesFromLastVisit));
//    }


    @Test
    void testShowEditDoctorForm() throws Exception {
        mockMvc.perform(get("/patient/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patient"));
    }

    @Test
    void testUpdateDoctor() throws Exception {

        mockMvc.perform(patch("/patient/1/edit")
                        .param("name", "John")
                        .param("surname", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("phone", "123-456-789")
                        .param("Country","Polska" )
                        .param("City","Poscien Zamion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));
    }
}