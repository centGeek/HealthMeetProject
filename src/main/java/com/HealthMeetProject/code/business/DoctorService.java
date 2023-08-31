package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.Specialization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorDAO doctorDAO;
    private final DoctorMapper doctorMapper;


    public List<Doctor> findAllAvailableDoctors() {
        List<Doctor> allAvailableDoctors = doctorDAO.findAllAvailableDoctors();
        log.info("Available doctors: [{}]", allAvailableDoctors.size());
        return allAvailableDoctors;
    }


    public Doctor findByEmail(String email) {
        Optional<Doctor> byEmail = doctorDAO.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new RuntimeException("Can not find doctor by email: [%s]".formatted(email));
        }
        return byEmail.get();
    }


    public List<Doctor> findAllBySpecialization(Specialization specialization) {
        List<Doctor> allBySpecialization = doctorDAO.findAllBySpecialization(specialization);
        log.info("allDoctorsBySpecialization: [{}]", allBySpecialization.size());
        return allBySpecialization;
    }


    public void addAvailabilityTime(Doctor doctor, OffsetDateTime beginTime, OffsetDateTime endTime) {
        doctorDAO.addAvailabilityTime(doctor, beginTime, endTime);
    }

    public void writeNote(Note note) {
        doctorDAO.writeNote(note);
    }

    public void issueReceipt(Receipt receipt) {
        doctorDAO.issueReceipt(receipt);
    }

   public void register(Doctor doctor){
//       DoctorDTO doctorDTO = doctorMapper.map(doctor);
//       doctorDAO.register(doctorDTO);
    }
}
