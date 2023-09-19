package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTOs;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.*;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.*;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
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
    private ReceiptJpaRepository receiptJpaRepository;
    private ReceiptEntityMapper receiptEntityMapper;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MedicineJpaRepository medicineJpaRepository;
    private DoctorMapper doctorMapper;


    @Override
    public List<Doctor> findAllAvailableDoctors() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public DoctorDTOs findAllDoctors() {
        List<DoctorDTO> list = doctorJpaRepository.findAll().stream().map(doctorEntityMapper::mapFromEntity).map(doctorMapper::mapToDTO).toList();
      return DoctorDTOs
                .builder()
                .doctorDTOList(list)
                .build();
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorJpaRepository.findByEmail(email)
                .map(doctorEntityMapper::mapFromEntity);
    }


    @Override
    public void writeNote(NoteEntity note) {
        noteJpaRepository.saveAndFlush(note);
    }

    @Override
    public void issueReceipt(Receipt receipt, Set<MedicineEntity> medicineSet) {
        ReceiptEntity receiptEntity = receiptEntityMapper.mapToEntity(receipt);
        ReceiptEntity receiptEntitySaved = receiptJpaRepository.saveAndFlush(receiptEntity);
        for (MedicineEntity medicine : medicineSet) {
            medicine.setReceipt(receiptEntitySaved);
        }
        medicineJpaRepository.saveAllAndFlush(medicineSet);
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

    @Override
    public Optional<Doctor> findById(Integer id) {
        return doctorJpaRepository.findById(id).map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public boolean findAnyTermInGivenRangeInGivenDay(OffsetDateTime since, OffsetDateTime toWhen, String doctorEmail) {
        List<AvailabilityScheduleEntity> anyTermInGivenRangeInGivenDay = availabilityScheduleJpaRepository.findAnyTermInGivenRangeInGivenDay(since, toWhen, doctorEmail);
        return anyTermInGivenRangeInGivenDay.isEmpty();
    }

    @Override
    public void save(Doctor doctor) {
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        doctorJpaRepository.save(doctorEntity);
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
                .earningsPerVisit(doctorDTO.getEarningsPerVisit())
                .user(userEntity).build();
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
                .userName(doctorDTO.getUser().getUserName())
                .password(doctorDTO.getUser().getPassword())
                .roles(Set.of(roleRepository.findByRole("DOCTOR")))
                .email(doctorDTO.getEmail())
                .build();
    }

    private void encodePassword(DoctorEntity doctorEntity, DoctorDTO doctorDTO) {
        doctorEntity.getUser().setPassword(passwordEncoder.encode(doctorDTO.getUser().getPassword()));
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