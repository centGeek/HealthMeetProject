package com.HealthMeetProject.code.api.controller.rest.integration;

import com.HealthMeetProject.code.api.controller.rest.integration.support.AvailabilityScheduleControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.MeetingRequestControllerTestSupport;
import com.HealthMeetProject.code.api.controller.rest.integration.support.ReceiptControllerTestSupport;
import com.HealthMeetProject.code.api.dto.MedicineDTO;
import com.HealthMeetProject.code.api.dto.MeetingRequestDTO;
import com.HealthMeetProject.code.api.dto.api.IssueReceiptDTO;
import com.HealthMeetProject.code.api.dto.api.Receipts;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.util.*;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReceiptControllerTestRestAssuredIT extends RestAssuredIntegrationTestBase
        implements ReceiptControllerTestSupport, MeetingRequestControllerTestSupport,
        AvailabilityScheduleControllerTestSupport {

    public AvailabilityScheduleDAO availabilityScheduleDAO;
    public DoctorEntityMapper doctorEntityMapper;
    public DoctorRepository doctorRepository;
    public PatientRepository patientRepository;

    @Test
    public void thatMedicineIsAddedCorrectly() {
        MedicineDTO medicine = MedicineExampleFixtures.medicineDTOExampleData1();
        List<MedicineDTO> medicineDTOList = new ArrayList<>();
        medicineDTOList.add(medicine);
        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();

        Patient patient1 = PatientExampleFixtures.patientExample1();
        patient1.setUser(PatientDTOFixtures.userDataPatient());
        patientRepository.savePatient(patient1);

        Doctor doctor1 = DoctorExampleFixtures.doctorExample1();
        doctor1.setUser(DoctorDTOFixtures.userDataDoctor());
        doctorRepository.save(doctor1);

        Doctor doctor = doctorRepository.findByEmail(doctor1.getEmail()).orElseThrow();
        Patient patient = patientRepository.findByEmail(patient1.getEmail());
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        LocalDateTime since = LocalDateTime.of(2026, 3, 25, 8, 0);
        LocalDateTime toWhen = LocalDateTime.of(2026, 3, 25, 16, 0);
        availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
        List<AvailabilitySchedule> availabilitySchedules = availabilityScheduleDAO.restFindAllAvailableTermsByGivenDoctor(doctor.getEmail());

        MeetingRequestDTO meetingRequestDTO = MeetingRequestDTO.builder().description(meetingRequest.getDescription())
                .patientEmail(patient.getEmail())
                .availabilityScheduleId(availabilitySchedules.get(0).getAvailability_schedule_id())
                .build();
        addMeetingRequest(meetingRequestDTO);
        IssueReceiptDTO issueReceiptDTO = IssueReceiptDTO.builder().patient(patient).doctor(doctor).medicineDTOList(medicineDTOList).build();
        issueReceipt(issueReceiptDTO);

        Receipts receiptPage = getReceiptPage(patient.getEmail());
        Assertions.assertEquals(receiptPage.getReceipts().get(0).getMedicine().size()
                ,medicineDTOList.size());
    }
}
