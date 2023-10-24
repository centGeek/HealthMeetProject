package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.business.ReceiptService;
import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
import com.HealthMeetProject.code.domain.Medicine;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.util.MedicineExampleFixtures;
import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReceiptController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiptControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MeetingRequestDAO meetingRequestDAO;

    @MockBean
    private ReceiptService receiptService;

    @Test
    void thatReceiptPageIsLoadedCorrectly() throws Exception {
        Integer meetingId = 4;

        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
        List<Medicine> medicineList = new ArrayList<>();

        when(meetingRequestDAO.findById(meetingId)).thenReturn(meetingRequest);

        mockMvc.perform(get(ReceiptController.RECEIPT_PAGE, meetingId)
                        .sessionAttr("medicineList", medicineList))
                .andExpect(model().attributeExists("now"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("doctor"))
                .andExpect(model().attributeExists("meetingId"))
                .andExpect(model().attributeExists("medicineList"))
                .andExpect(view().name("receipt"));
    }

    @Test
    void thatMedicineIsAddedSuccessfully() throws Exception {
        Integer meetingId = 4;

        List<MedicineDTO> medicineList = new ArrayList<>();
        String medicineName = "Paracetamol";
        int quantity = 2;
        when(meetingRequestDAO.findById(any())).thenReturn(MeetingRequestsExampleFixtures.meetingRequestDataExample1());

        BigDecimal approxPrice = new BigDecimal("10.00");
        mockMvc.perform(post(ReceiptController.RECEIPT_PAGE_ADD_MEDICINE, meetingId)
                        .param("medicine_name", medicineName)
                        .param("quantity", String.valueOf(quantity))
                        .param("approx_price", approxPrice.toString())
                        .sessionAttr("medicineList", medicineList))
                .andExpect(redirectedUrl("/doctor/issue/receipt/4"));

        assertEquals(1, medicineList.size());
        MedicineDTO addedMedicine = medicineList.get(0);
        assertEquals(medicineName, addedMedicine.getName());
        assertEquals(quantity, addedMedicine.getQuantity());
        assertEquals(approxPrice, addedMedicine.getApproxPrice());
    }

    @Test
    void thatReceiptIsIssuedSuccessfully() throws Exception {
        Integer meetingId = 4;

        List<MedicineDTO> medicineList = new ArrayList<>();
        medicineList.add(MedicineExampleFixtures.medicineDTOExampleData1());

        doNothing().when(receiptService).issueReceipt(medicineList, MeetingRequestsExampleFixtures.meetingRequestDataExample1().getPatient());
        when(meetingRequestDAO.findById(meetingId)).thenReturn(MeetingRequestsExampleFixtures.meetingRequestDataExample1());
        mockMvc.perform(post(ReceiptController.RECEIPT_PAGE_ADD, meetingId)
                        .sessionAttr("medicineList", medicineList))
                .andExpect(redirectedUrl("/doctor"));

        verify(receiptService, times(1)).issueReceipt(eq(medicineList), any());
    }
}
