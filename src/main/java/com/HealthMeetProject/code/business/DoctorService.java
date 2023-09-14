package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.AccessDeniedException;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.NoteRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorDAO doctorDAO;
    private final DoctorEntityMapper doctorEntityMapper;
    private final PatientEntityMapper patientEntityMapper;
    private final NoteRepository noteRepository;


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




    @Transactional
    public void writeNote(Doctor doctor, String illness, String description, Patient patient, OffsetDateTime visitStart, OffsetDateTime visitEnd) {
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        PatientEntity patientEntity = patientEntityMapper.mapToEntity(patient);
        NoteEntity build = NoteEntity.builder()
                .doctor(doctorEntity)
                .patient(patientEntity)
                .description(description)
                .illness(illness)
                .startTime(visitStart)
                .endTime(visitEnd)
                .build();
        if(noteRepository.isThereNoteWithTheSameTimeVisitAndDoctor(build.getStartTime(), build.getEndTime(), doctor.getEmail())){
            throw new UserAlreadyExistsException("Note with following visit already exist");
        }
        doctorDAO.writeNote(build);
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
