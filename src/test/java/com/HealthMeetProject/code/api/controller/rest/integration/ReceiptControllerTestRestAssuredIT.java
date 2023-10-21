//package com.HealthMeetProject.code.api.controller.rest.integration;
//
//import com.HealthMeetProject.code.api.controller.rest.integration.support.*;
//import com.HealthMeetProject.code.api.dto.*;
//import com.HealthMeetProject.code.business.MeetingRequestService;
//import com.HealthMeetProject.code.business.ReceiptService;
//import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
//import com.HealthMeetProject.code.domain.AvailabilitySchedule;
//import com.HealthMeetProject.code.domain.Doctor;
//import com.HealthMeetProject.code.domain.MeetingRequest;
//import com.HealthMeetProject.code.domain.Patient;
//import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
//import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
//import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.MeetingRequestRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MedicineJpaRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
//import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
//import com.HealthMeetProject.code.util.DoctorDTOFixtures;
//import com.HealthMeetProject.code.util.MedicineExampleFixtures;
//import com.HealthMeetProject.code.util.MeetingRequestsExampleFixtures;
//import com.HealthMeetProject.code.util.PatientDTOFixtures;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReceiptControllerTestRestAssuredIT extends RestAssuredIntegrationTestBase
//        implements ReceiptControllerTestSupport, MeetingRequestControllerTestSupport,
//        PatientControllerTestSupport, DoctorsControllerTestSupport, AvailabilityScheduleControllerTestSupport {
//
//    @Autowired
//    public MedicineJpaRepository medicineJpaRepository;
//
//    @Autowired
//    public AvailabilityScheduleDAO availabilityScheduleDAO;
//    @Autowired
//    public DoctorEntityMapper doctorEntityMapper;
//
//    @Autowired
//    public ReceiptService receiptService;
//
//
//    @Autowired
//    public MeetingRequestRepository meetingRequestRepository;
//    @Autowired
//    public MeetingRequestJpaRepository meetingRequestJpaRepository;
//    @Autowired
//    public DoctorRepository doctorRepository;
//    @Autowired
//    public PatientRepository patientRepository;
//    @Autowired
//    public MeetingRequestService meetingRequestService;
//
//    @Test
//    public void thatMedicineIsAddedCorrectly() {
//        MedicineDTO medicine = MedicineExampleFixtures.medicineDTOExampleData1();
//        List<MedicineDTO> medicineDTOList = new ArrayList<>();
//        medicineDTOList.add(medicine);
//        MeetingRequest meetingRequest = MeetingRequestsExampleFixtures.meetingRequestDataExample1();
//
//        PatientDTO patient1 = PatientDTOFixtures.patientDTOExample1();
//        patient1.setUser(PatientDTOFixtures.userDataPatient());
//        savePatient(patient1);
//
//        DoctorDTO doctor1 = DoctorDTOFixtures.getDoctorDTO1();
//        doctor1.setUser(DoctorDTOFixtures.userDataDoctor());
//        saveDoctor(doctor1);
//
//        Doctor doctor = doctorRepository.findByEmail(doctor1.getEmail()).orElseThrow();
//        Patient patient = patientRepository.findByEmail(patient1.getEmail());
//        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
//        LocalDateTime since = LocalDateTime.of(2026, 3, 25, 8, 0);
//        LocalDateTime toWhen = LocalDateTime.of(2026, 3, 25, 16, 0);
//        availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
//        List<AvailabilitySchedule> availabilitySchedules = availabilityScheduleDAO.restFindAllAvailableTermsByGivenDoctor(doctor.getEmail());
//
//        MeetingRequestDTO meetingRequestDTO = MeetingRequestDTO.builder().description(meetingRequest.getDescription())
//                .patientEmail(patient.getEmail())
//                .availabilityScheduleId(availabilitySchedules.get(0).getAvailability_schedule_id())
//                .build();
//        addMeetingRequest(meetingRequestDTO);
//        receiptService.restIssueReceipt(medicineDTOList, patient, doctor);
//        ReceiptDTO receiptPage = getReceiptPage(patient.getEmail());
//        Assertions.assertNotNull(receiptPage.getNow());
//    }
//}
