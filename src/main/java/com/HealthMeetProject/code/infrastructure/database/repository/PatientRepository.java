package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.*;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.VisitInvoiceJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.VisitInvoiceEntityMapper;
import com.HealthMeetProject.code.infrastructure.security.RoleRepository;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final VisitInvoiceJpaRepository visitInvoiceJpaRepository;
    private final MeetingRequestJpaRepository meetingRequestJpaRepository;

    private final PatientEntityMapper patientEntityMapper;
    private final VisitInvoiceEntityMapper visitInvoiceEntityMapper;
    private final MeetingRequestEntityMapper meetingRequestEntityMapper;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void issueInvoice(Patient patient) {
        PatientEntity patientToSave = patientEntityMapper.mapToEntity(patient);
        PatientEntity patientSaved = patientJpaRepository.saveAndFlush(patientToSave);

        patient.getInvoices().stream()
                .filter(invoice -> Objects.isNull(invoice.getInvoiceId()))
                .map(visitInvoiceEntityMapper::mapToEntity)
                .forEach(invoiceEntity -> {
                    invoiceEntity.setPatient(patientSaved);
                    visitInvoiceJpaRepository.saveAndFlush(invoiceEntity);
                });
    }

    @Override
    public void saveMeetingRequest(MeetingRequest meetingRequest, Patient patient) {
        MeetingRequestEntity meetingRequestEntity = meetingRequestEntityMapper.mapToEntity(meetingRequest);
        PatientEntity patientEntity = patientEntityMapper.mapToEntity(patient);
        meetingRequestEntity.setPatient(patientEntity);
        meetingRequestJpaRepository.saveAndFlush(meetingRequestEntity);

    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity toSave = patientEntityMapper.mapToEntity(patient);
        PatientEntity saved = patientJpaRepository.save(toSave);
        return patientEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void register(PatientDTO patientDTO) {
        UserEntity userEntity = getPatientEntityToRegister(patientDTO);
        AddressEntity addressEntity = getAddressEntityToRegister(patientDTO);
        PatientEntity patientEntity = getPatientEntityToRegister(patientDTO, userEntity, addressEntity);
        encodePassword(patientEntity, patientDTO);
        userRepository.saveAndFlush(userEntity);
        patientJpaRepository.saveAndFlush(patientEntity);
    }

    private  PatientEntity getPatientEntityToRegister(PatientDTO patientDTO, UserEntity userEntity, AddressEntity addressEntity) {
        return PatientEntity.builder()
                .name(patientDTO.getName())
                .surname(patientDTO.getSurname())
                .email(patientDTO.getEmail())
                .pesel(patientDTO.getPesel())
                .phone(patientDTO.getPhone())
                .address(addressEntity)
                .user(userEntity).build();
    }

    private AddressEntity getAddressEntityToRegister(PatientDTO patientDTO) {
        return AddressEntity.builder()
                .city(patientDTO.getAddress().getCity())
                .country(patientDTO.getAddress().getCountry())
                .postalCode(patientDTO.getAddress().getPostalCode())
                .address(patientDTO.getAddress().getAddress())
                .build();
    }

    private UserEntity getPatientEntityToRegister(PatientDTO patientDTO) {
        return UserEntity.builder()
                .active(true)
                .userName(patientDTO.getUser().getUserName())
                .password(patientDTO.getUser().getPassword())
                .roles(Set.of(roleRepository.findByRole("PATIENT")))
                .email(patientDTO.getEmail())
                .build();
    }

    private void encodePassword(PatientEntity patientEntity, PatientDTO patientDTO) {
        patientEntity.getUser().setPassword(passwordEncoder.encode(patientDTO.getUser().getPassword()));
    }
}