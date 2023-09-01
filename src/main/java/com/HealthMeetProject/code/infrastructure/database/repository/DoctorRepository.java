package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.*;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.*;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.NoteEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {
    private DoctorJpaRepository doctorJpaRepository;
    private DoctorEntityMapper doctorEntityMapper;
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private NoteJpaRepository noteJpaRepository;
    private NoteEntityMapper noteEntityMapper;
    private ReceiptJpaRepository receiptJpaRepository;
    private ReceiptEntityMapper receiptEntityMapper;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Doctor> findAllAvailableDoctors() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorJpaRepository.findByEmail(email)
                .map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public List<Doctor> findAllBySpecialization(Specialization specialization) {
        return doctorJpaRepository.findAllBySpecialization(specialization).stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void addAvailabilityTime(Doctor doctor, OffsetDateTime beginTime, OffsetDateTime endTime) {
        DoctorEntity doctorToSave = doctorEntityMapper.mapToEntity(doctor);
        Set<AvailabilityScheduleEntity> terms = availabilityScheduleJpaRepository.findAllTermsByGivenDoctor(doctor.getEmail());
        AvailabilityScheduleEntity availabilityScheduleToSave = AvailabilityScheduleEntity.builder().since(beginTime).toWhen(endTime).doctor(doctorToSave).build();
        terms.add(availabilityScheduleToSave);
        availabilityScheduleJpaRepository.saveAndFlush(availabilityScheduleToSave);
    }

    @Override
    public void writeNote(Note note) {
        NoteEntity noteEntity = noteEntityMapper.map(note);
        noteJpaRepository.saveAndFlush(noteEntity);
    }

    @Override
    public void issueReceipt(Receipt receipt) {
        ReceiptEntity receiptEntity = receiptEntityMapper.map(receipt);
        receiptJpaRepository.saveAndFlush(receiptEntity);
    }

    @Override
    public void register(DoctorDTO doctorDTO) {
        UserEntity userEntity = getUserEntityToRegister(doctorDTO);
        ClinicEntity clinicEntity = getClinicEntityToRegister(doctorDTO);
        DoctorEntity doctorEntity = getDoctorEntityToRegister(doctorDTO, userEntity, clinicEntity);
        conditionsToNotCreateDoctor(doctorDTO);
        encodePassword(doctorEntity, doctorDTO);
        userRepository.saveAndFlush(userEntity);
        doctorJpaRepository.saveAndFlush(doctorEntity);
    }

    private void conditionsToNotCreateDoctor(DoctorDTO doctorDTO) {
        if (isEmailAlreadyExists(doctorDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email: [%s] already exists".formatted(doctorDTO.getEmail()));
        }
        if (isPhoneAlreadyExists(doctorDTO.getPhone())) {
            throw new UserAlreadyExistsException("User with email: [%s] already exists".formatted(doctorDTO.getEmail()));
        }
    }

    private static DoctorEntity getDoctorEntityToRegister(DoctorDTO doctorDTO, UserEntity userEntity, ClinicEntity clinicEntity) {
        return DoctorEntity.builder()
                .name(doctorDTO.getName())
                .clinic(clinicEntity)
                .surname(doctorDTO.getSurname())
                .email(doctorDTO.getEmail())
                .specialization(doctorDTO.getSpecialization())
                .phone(doctorDTO.getPhone())
                .salaryFor15minMeet(doctorDTO.getSalaryFor15minMeet())
                .userEntity(userEntity).build();
    }

    private static ClinicEntity getClinicEntityToRegister(DoctorDTO doctorDTO) {
        return ClinicEntity.builder()
                .clinicName(doctorDTO.getClinic().getClinicName())
                .country(doctorDTO.getClinic().getCountry())
                .address(doctorDTO.getClinic().getAddress())
                .postalCode(doctorDTO.getClinic().getPostalCode())
                .build();
    }

    private UserEntity getUserEntityToRegister(DoctorDTO doctorDTO) {
        return UserEntity.builder()
                .active(true)
                .userName(doctorDTO.getUserData().getUserName())
                .password(doctorDTO.getUserData().getPassword())
                .roles(Set.of(roleRepository.findByRole("DOCTOR")))
                .email(doctorDTO.getEmail())
                .build();
    }

    private void encodePassword(DoctorEntity doctorEntity, DoctorDTO doctorDTO) {
        doctorEntity.getUserEntity().setPassword(passwordEncoder.encode(doctorDTO.getUserData().getPassword()));
    }

    private boolean isEmailAlreadyExists(String email) {
        Optional<DoctorEntity> existingDoctorByEmail = doctorJpaRepository.findByEmail(email);
        return existingDoctorByEmail.isPresent();
    }

    private boolean isPhoneAlreadyExists(String phone) {
        Optional<DoctorEntity> existingDoctorByPhone = doctorJpaRepository.findByEmail(phone);

        return existingDoctorByPhone.isPresent();
    }


}