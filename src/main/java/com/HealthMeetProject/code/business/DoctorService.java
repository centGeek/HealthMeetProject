package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.*;
import com.HealthMeetProject.code.domain.exception.AccessDeniedException;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorDAO doctorDAO;
    private final DoctorMapper doctorMapper;
    private final DoctorJpaRepository doctorJpaRepository;


    public List<Doctor> findAllAvailableDoctors() {
        if (!userHasPatientPermission()) {
            throw new AccessDeniedException("You have no access to this resource.");
        }
        List<Doctor> allAvailableDoctors = doctorDAO.findAllAvailableDoctors();
        log.info("Available doctors: [{}]", allAvailableDoctors.size());
        return allAvailableDoctors;
    }

    public boolean userHasPatientPermission() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("PATIENT"));
        }
        return false;
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


    @Transactional
    public void addNoteToDatabase(NoteEntity note){
        doctorDAO.writeNote(note);
    }

    public NoteEntity writeNote(DoctorEntity doctor, String illness, String description, PatientEntity patient) {
        NoteEntity build = NoteEntity.builder()
                .doctor(doctor)
                .patient(patient)
                .description(description)
                .illness(illness)
                .startTime(OffsetDateTime.now())
                .endTime(OffsetDateTime.now())
                .build();
        doctorDAO.writeNote(build);
        return build;
    }


    @Transactional
    public void register(DoctorDTO doctorDTO) throws UserAlreadyExistsException {
        doctorDAO.register(doctorDTO);
    }

    public Optional<Doctor> findById(Integer doctorId) {
        return doctorDAO.findById(doctorId);
    }

    public String authenticateDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                email = userDetails.getUsername();
            }
        }
        Objects.requireNonNull(email);
        return email;
    }

    public boolean findAnyTermInGivenRangeInGivenDay(OffsetDateTime since, OffsetDateTime toWhen, String doctorEmail) {
        return doctorDAO.findAnyTermInGivenRangeInGivenDay(since, toWhen, doctorEmail);
    }
    public  void conditionsToUpdate(DoctorDTO updatedDoctorDTO, Doctor existingDoctor) {
        if (updatedDoctorDTO.getName() != null) {
            existingDoctor.setName(updatedDoctorDTO.getName());
        }
        if (updatedDoctorDTO.getSurname() != null) {
            existingDoctor.setSurname(updatedDoctorDTO.getSurname());
        }
        if (updatedDoctorDTO.getSpecialization() != null) {
            existingDoctor.setSpecialization(updatedDoctorDTO.getSpecialization());
        }
        if (updatedDoctorDTO.getEmail() != null) {
            existingDoctor.setEmail(updatedDoctorDTO.getEmail());
            existingDoctor.getUser().setEmail(updatedDoctorDTO.getEmail());
        }
        if (updatedDoctorDTO.getPhone() != null) {
            existingDoctor.setPhone(updatedDoctorDTO.getPhone());
        }
        if (updatedDoctorDTO.getEarningsPerVisit() != null) {
            existingDoctor.setEarningsPerVisit(updatedDoctorDTO.getEarningsPerVisit());
        }
    }
}
